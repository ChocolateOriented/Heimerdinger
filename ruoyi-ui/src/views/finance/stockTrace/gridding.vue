<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="400px">
        <h3>追踪计划</h3>
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称" />
          </el-form-item>
          <el-form-item label="代码" prop="code">
            <el-input v-model="form.code" placeholder="请输入代码" />
          </el-form-item>
          <el-form-item label="追踪价格" prop="price">
            <el-input v-model="form.price" placeholder="请输入价格" />
          </el-form-item>
          <el-form-item label="追踪市净率" prop="pb">
            <el-input v-model="form.pb" placeholder="请输入市净率" />
          </el-form-item>
          <el-form-item label="预计最低市净率" prop="pbMin">
            <el-input v-model="form.pbMin" placeholder="请输入预计最低市净率" />
          </el-form-item>
          <el-form-item label="预计最高市净率" prop="pbMax">
            <el-input v-model="form.pbMax" placeholder="请输入预计最高市净率" />
          </el-form-item>
          <el-form-item label="预计合理市净率" prop="pbFit">
            <el-input v-model="form.pbFit" placeholder="请输入预计合理市净率" />
          </el-form-item>
          <el-form-item label="定投时间" prop="keepData">
            <el-date-picker clearable size="small"
                            v-model="form.keepData"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择持有时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="开始追踪日期" prop="keepData">
            <el-date-picker clearable size="small"
                            v-model="form.startTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择持有时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="持有金额" prop="amount">
            <el-input v-model="form.amount" placeholder="请输入持有金额" />
          </el-form-item>
          <el-form-item label="最小持有金额" prop="amountMin">
            <el-input v-model="form.amountMin" placeholder="请输入最小持有金额" />
          </el-form-item>
          <el-form-item label="最大持有金额" prop="amountMax">
            <el-input v-model="form.amountMax" placeholder="请输入最大持有金额" />
          </el-form-item>
          <el-form-item label="合理持有金额" prop="amountFit">
            <el-input v-model="form.amountFit" placeholder="请输入合理持有金额" />
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </el-aside>
      <el-container>
        <el-header height="160px">
          <el-descriptions
          class="margin-top"
          title="交易计划"
          :column="6"
          border
          >
            <el-descriptions-item label="交易次数">{{ plan.time }}</el-descriptions-item>
            <el-descriptions-item label="交易金额">{{ plan.amount }}</el-descriptions-item>
            <el-descriptions-item label="下跌触发">{{ plan.unit }}</el-descriptions-item>
            <el-descriptions-item label="合理价格">{{ plan.price }}</el-descriptions-item>
            <el-descriptions-item label="定投日">周: <el-input v-model="plan.week" style="width: 4em" v-on:change="computeTable"/></el-descriptions-item>
            <el-descriptions-item label="时间弹性"> <el-input v-model="plan.elasticity" style="width: 4em" v-on:change="computeTable"/></el-descriptions-item>
            <el-descriptions-item label="当前价格">{{ current.price }}</el-descriptions-item>
<!--            <el-descriptions-item label="当前市净率">{{ current.pb }}</el-descriptions-item>-->
            <el-descriptions-item label="预计上涨">{{ plan.rise }}%</el-descriptions-item>
            <el-descriptions-item label="预计下跌">{{ plan.fall }}%</el-descriptions-item>
          </el-descriptions>
        </el-header>
        <el-main>
          <template>
            <el-table
              :data="adviceTabel"
              style="width: 100%"
              max-height="650px"
              :row-class-name="dealAdvice"
            >
              <el-table-column prop="date" label="触发日期" :formatter="dateFormat" width="180" />
              <el-table-column prop="advicePrice" label="触发价格" width="180" />
              <el-table-column prop="adviceAmount" label="持仓" />
              <el-table-column prop="advicePercent" label="持仓百分比" />
            </el-table>
          </template>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import { listStockTrace, getStockTrace, delStockTrace, addStockTrace, updateStockTrace, exportStockTrace, findCurrentInfo } from "@/api/finance/stockTrace";
  import moment from "moment"

export default {
  name: "StockTraceGridding",
  data() {
    return {
       // 遮罩层
      form:{},
      plan:{
        week:4,
        elasticity:2,
      },
      loading: true,
      adviceTabel:[],
      current:{},
    };
  },
  created() {
    const id = this.$route.params && this.$route.params.id;
    let that = this;
    if (id) {
      this.loading = true;
      getStockTrace(id).then((response) => {
        that.form = response.data;
        that.plan.tradeDate = that.getDate(that.form.startTime,that.form.keepData, that.plan.week);
        that.plan.time = that.plan.tradeDate.length;
        that.plan.amount = that.floatFormat((that.form.amountFit - that.form.amount)/that.plan.time) ;
        that.plan.unit = ((that.form.pb - that.form.pbMin)/that.form.pb)* that.form.price / that.plan.time ;
        that.plan.price = that.floatFormat(that.form.price * that.form.pbFit / that.form.pb) ;
        that.computeTable();
      });


    }
  },
  methods: {
    /** 单击选中行数据 */
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.roleIds = selection.map((item) => item.roleId);
    },
    // 保存选中的数据编号
    getRowKey(row) {
      return row.roleId;
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStockTrace(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStockTrace(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    getDate(startDate, endDate, weak){
      weak = weak - 1;
      let dates = [];
      let dateTime = new Date(startDate);
      let endDateTime = new Date(endDate);
      while (dateTime < endDateTime){
        dateTime.setDate(dateTime.getDate() + 1);
        let day = dateTime.getDay();
        if (day === weak){
          dates.push(new Date(dateTime));
        }
      }
      return dates;
    },
    dateFormat:function(row,column){
      let date = row[column.property];
      if(date === undefined){
        return ''
      }
      return moment(date).format("YYYY-MM-DD")
    },
    floatFormat(number){
      return Math.round(number * 1000) / 1000;
    },
    computeTable(){
      let that = this;
      this.loading = false;
      //获取当前股票信息
      findCurrentInfo({"code":that.form.code}).then((response) => {
        that.current = response.data;

        //定投计划
        let floorPrice = 0;
        for (let i = 0; i < that.plan.tradeDate.length; i++) {
          let advicePrice = that.floatFormat(that.form.price- i*that.plan.unit);
          let adviceAmount = that.floatFormat(that.form.amount + i* that.plan.amount);
          let advicePercent = that.floatFormat(adviceAmount*100/that.form.amountFit);
          floorPrice = advicePrice;
          that.adviceTabel.push({
            "date": that.plan.tradeDate[i],
            "advicePrice": advicePrice,
            "adviceAmount":adviceAmount,
            "advicePercent":advicePercent,
          });
        }

        that.plan.rise = that.floatFormat((that.plan.price - that.current.price)*100/that.current.price);
        that.plan.fall = that.floatFormat((that.current.price - floorPrice)*100/that.current.price);

        //定投计划风险分级
        let buyNum = 0;
        for (let i = 0; i <  that.adviceTabel.length; i++) {
          const advice = that.adviceTabel[i];
          if ( new Date() > advice.date){
            advice.buyCss = "buy-row";
            buyNum ++;
            continue;
          }
          if ( advice.advicePrice > this.current.price){
            if ( i >= buyNum *that.plan.elasticity ){
              advice.buyCss = 'buy-danger-row';
              continue;
            }
            advice.buyCss = 'buy-cautious-row';
            continue;
          }
          advice.buyCss = '';
        }

      })
    },
    dealAdvice({row, rowIndex}) {
      return row.buyCss;
    }
  },
};
</script>

<style>
  .el-table .buy-row {
    background: #C6E997;
  }
  .el-table .buy-cautious-row {
    background: #e9d076;
  }
  .el-table .buy-danger-row {
    background: #ff9680;
  }
</style>
