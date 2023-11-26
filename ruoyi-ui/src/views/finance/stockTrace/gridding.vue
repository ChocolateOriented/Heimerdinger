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
          :column="5"
          border
          >
            <el-descriptions-item label="交易次数">{{ plan.time }}</el-descriptions-item>
            <el-descriptions-item label="交易金额">{{ plan.amount }}</el-descriptions-item>
            <el-descriptions-item label="下跌触发">{{ plan.unit }}</el-descriptions-item>
            <el-descriptions-item label="合理价格">{{ plan.priceFit }}</el-descriptions-item>
            <el-descriptions-item label="定投日">周: <el-input v-model="plan.week" style="width: 4em" v-on:change="computeTable"/></el-descriptions-item>
            <el-descriptions-item label="时间弹性">
              <el-input v-model="plan.elasticity" style="width: 4em" v-on:change="computeTable"/>
            </el-descriptions-item>
            <el-descriptions-item label="价格安全边际">
              <el-input v-model="plan.safety" style="width: 4em" v-on:change="computeTable"/>%
            </el-descriptions-item>
            <el-descriptions-item label="当前价格">{{ current.price }}</el-descriptions-item>
<!--            <el-descriptions-item label="当前市净率">{{ current.pb }}</el-descriptions-item>-->
            <el-descriptions-item label="上涨空间">{{ plan.rise }}%</el-descriptions-item>
            <el-descriptions-item label="下跌空间">{{ plan.fall }}%</el-descriptions-item>
          </el-descriptions>
        </el-header>
        <el-main>
          <template>
            <el-table
              :data="adviceTabel"
              style="width: 100%"
              max-height="650px"
              :cell-class-name="dealAdvice"
            >
              <el-table-column prop="advicePrice" label="触发价格" width="180" />
              <el-table-column prop="griddingAmount" label="网格持仓" />
              <el-table-column prop="griddingPercent" label="网格持仓百分比">
                <template slot-scope="scope">
                  {{ scope.row.griddingPercent }}
                  <el-tag v-if="scope.row.realGriddingAmount" style="margin-left: 3em">
                    当前持仓
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column prop="adviceDate" label="触发日期" :formatter="dateFormat" width="180" />
              <el-table-column prop="adviceAmount" label="定投持仓" />
              <el-table-column prop="advicePercent" label="定投持仓百分比" >
                <template slot-scope="scope">
                  {{ scope.row.advicePercent }}
                  <el-tag v-if="scope.row.realAmount" style="margin-left: 3em">
                    当前持仓
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    @click="handelPosition(scope.row)"
                  >调仓</el-button>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-main>
      </el-container>
    </el-container>

    <!-- 调仓对话框 -->
    <el-dialog title="调仓" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="来源" prop="name">
          <el-select v-model="sourceFinancePositionPlanId" placeholder="请选择" v-on:change="positionChange">
            <el-option
              v-for="item in listFinancePositionPlan"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="仓位" prop="realityAmount">
            <el-slider v-model="targetFinancePositionPlan.realityAmount"  style="width: 400px" :max="maxAmount" show-input></el-slider>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updatePosition">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { listStockTrace, getStockTrace, delStockTrace, addStockTrace, updateStockTrace, exportStockTrace, findCurrentInfo } from "@/api/finance/stockTrace";
  import { saveStockPositionPlanList } from "@/api/finance/stockPositionPlan";
  import { listFinancePositionPlan, getFinancePositionPlan, delFinancePositionPlan, addFinancePositionPlan, updateFinancePositionPlan, exportFinancePositionPlan } from "@/api/finance/financePositionPlan";
  import moment from "moment"

export default {
  name: "StockTraceGridding",
  data() {
    return {
      // 是否显示弹出层
      open: false,
       // 遮罩层
      form:{},
      plan:{
        week:4,
        elasticity:2,
        safety:50
      },
      loading: true,
      adviceTabel:[],
      current:{},
      financePositionPlan:null,
      sourceFinancePositionPlanId:5,
      sourceFinancePositionPlan:{},
      targetFinancePositionPlan:{},
      listFinancePositionPlan:[],
      maxAmount:0
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
        that.plan.priceFit = that.floatFormat(that.form.price * that.form.pbFit / that.form.pb) ;
        that.plan.priceMin = that.floatFormat(that.form.price * that.form.pbMin / that.form.pb) ;
        //TODO safety 安全边际逻辑修改
        //安全价格
        that.plan.priceSafe = (that.plan.priceFit + that.plan.priceMin)*that.plan.safety/100
        that.plan.unit = (that.plan.priceSafe - that.plan.priceMin) / that.plan.time ;

        that.computeTable();

        findCurrentInfo({"code":that.form.code}).then((response) => {
          that.current = response.data;
          that.plan.rise = that.floatFormat((that.plan.priceFit - that.current.price)*100/that.current.price);
          that.plan.fall = that.floatFormat((that.current.price - that.plan.floorPrice )*100/that.current.price);

          listFinancePositionPlan({"traceId":id}).then((response) => {
            if (response.rows.length > 0){
              that.financePositionPlan = response.rows[0];
            }
            that.computeTable();
          });
        })
      });
      this.findListFinancePositionPlan();
    }
  },
  methods: {
    /** 单击选中行数据 */
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    findListFinancePositionPlan(){
      listFinancePositionPlan().then((response) => {
        this.listFinancePositionPlan = response.rows;
      });
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
          this.computeTable();
          if (this.form.id != null) {
            updateStockTrace(this.form).then(response => {
              saveStockPositionPlanList(this.adviceTabel).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
              });
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
      //定投计划
      let floorPrice = 0;
      that.adviceTabel = [];
      for (let i = 0; i < that.plan.tradeDate.length; i++) {
        let advicePrice = that.floatFormat(Number(that.plan.priceSafe )- i*that.plan.unit);
        let adviceAmount = that.floatFormat(Number(that.form.amount) + i* that.plan.amount);
        let advicePercent = that.floatFormat(adviceAmount*100/Number(that.form.amountFit));
        let griddingPercent = that.floatFormat((i+1)*100/that.plan.tradeDate.length)
        let griddingAmount = (griddingPercent * that.form.amountFit)/100;
        floorPrice = advicePrice;
        that.adviceTabel.push({
          "traceId": that.form.id,
          "adviceDate": that.plan.tradeDate[i],
          "advicePriadvicePriceadvicePricece": advicePrice,
          "adviceAmount":adviceAmount,
          "advicePercent":advicePercent,
          "griddingAmount":griddingAmount,
          "griddingPercent":griddingPercent,
        });
      }
      that.plan.floorPrice = floorPrice;

      //定投计划风险分级
      let buyNum = 0;
      let realityAmount = 0;
      let realityAmountIndex = null;
      let realityGriddingAmountIndex = null;
      if(that.financePositionPlan != null){
        realityAmount = that.financePositionPlan.realityAmount * this.current.price  ;
      }
      for (let i = 0; i <  that.adviceTabel.length; i++) {
        const advice = that.adviceTabel[i];
        advice.hadBuy = "";
        if (realityAmount >= advice.adviceAmount) {
          realityAmountIndex = i;
          advice.hadBuy = "hadBuy";
        }
        if (realityAmount >= advice.griddingAmount) {
          realityGriddingAmountIndex = i;
          advice.griddingHadBuy = "hadBuy";
        }
        if ( new Date() > advice.adviceDate){
          //建议买入
          advice.dateCss = "buy-row";
          buyNum ++;
          continue;
        }
        //TODO 这是干嘛的??
        if ( advice.advicePrice >= this.current.price){
          //超过定投目标时间弹性倍数后建议卖出
          if ( i >= buyNum * that.plan.elasticity ){
            //建议卖出
            advice.priceCss = 'buy-danger-row';
            continue;
          }
          //建议持有
          advice.priceCss = 'buy-cautious-row';
          continue;
        }
        advice.buyCss = '';
      }
      if (realityAmountIndex != null){
        that.adviceTabel[realityAmountIndex].realAmount = true;
      }
      if (realityGriddingAmountIndex != null){
        that.adviceTabel[realityGriddingAmountIndex].realGriddingAmount = true;
      }

    },
    dealAdvice({row, column, rowIndex, columnIndex}) {
      if (column.property ==  "advicePrice" || column.property == "griddingAmount" || column.property == "griddingPercent") {
        if (row.priceCss!=null) {
          return row.priceCss;
        }
        return row.griddingHadBuy;
      }
      if ( column.property == "adviceDate" || column.property == "adviceAmount" || column.property == "advicePercent" ) {
        if (row.dateCss!=null) {
          return row.dateCss;
        }
        return row.hadBuy;
      }
      return "";
    },
    handelPosition(row){
      this.targetFinancePositionPlan.realityAmount = row.adviceAmount;
      this.positionChange(this.sourceFinancePositionPlanId);
      this.open = true;
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    updatePosition() {
      let financePositionPlan = this.financePositionPlan;
      if (financePositionPlan!= null) {
          financePositionPlan.realityAmount = this.targetFinancePositionPlan.realityAmount;
          financePositionPlan.targetAmount = this.form.amountFit;
          updateFinancePositionPlan(financePositionPlan).then(response => {
            this.$modal.msgSuccess("调仓成功");
            this.computeTable();
            this.open = false;
          });
        } else {
          financePositionPlan = {
            "realityAmount": row.adviceAmount,
            "traceId": this.form.id,
            "name": this.form.name,
            "targetAmount": this.form.amountFit,
          };
          addFinancePositionPlan(financePositionPlan).then(response => {
            this.$modal.msgSuccess("调仓成功");
            listFinancePositionPlan({"traceId":this.form.id}).then((response) => {
              if (response.rows.length > 0){
                this.financePositionPlan = response.rows[0];
              }
              this.computeTable();
              this.open = false;
            });
          });
        }
        this.sourceFinancePositionPlan.realityAmount = this.maxAmount - financePositionPlan.realityAmount;
        updateFinancePositionPlan(this.sourceFinancePositionPlan).then(r=>{
          this.findListFinancePositionPlan();
        });
    },
    positionChange(id){
      let listFinancePositionPlan = this.listFinancePositionPlan;
      for (let i = 0; i < listFinancePositionPlan.length; i++) {
        let financePositionPlan = listFinancePositionPlan[i];
        if (id == financePositionPlan.id){
          this.sourceFinancePositionPlan = financePositionPlan;
          break;
        }
      }
      this.maxAmount = this.sourceFinancePositionPlan.realityAmount + this.financePositionPlan.realityAmount;
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
  .el-table .hadBuy {
    background: #e8f4ff;
  }
</style>
