<template>
  <el-container>
    <el-header height="400px">
      <el-row :gutter="20">
        <el-col :span="8"><div class="grid-content bg-purple" style="height: 400px" id="positionPie" >

        </div></el-col>
        <el-col :span="16"><div class="grid-content bg-purple" style="height: 400px" id="positionBar" >

        </div></el-col>
      </el-row>
    </el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="12"><div class="grid-content bg-purple">
          <h4>交易建议</h4>
          <el-table v-loading="loading" :data="tradeAdviceList" show-summary @row-dblclick="gotoGridding" :row-class-name="dealAdvice">
            <el-table-column label="资产名称" align="center" prop="name" />
            <el-table-column label="当前价格" align="center" prop="currentPrice" sortable />
            <el-table-column label="实际持仓" align="center" prop="realityAmount" sortable />
            <el-table-column label="建议持仓" align="center" prop="adviceAmount" sortable />
          </el-table>
        </div></el-col>
        <el-col :span="12"><div class="grid-content bg-purple">
          <h4>机会排行</h4>
          <el-table v-loading="loading"
                    :data="tradeGradeList"
                    stripe
                    show-summary
                    :default-sort = "{prop: 'grade', order: 'descending'}"
                    @row-dblclick="gotoGridding">
            <el-table-column label="资产名称" align="center" prop="name" />
            <el-table-column label="机会评分" align="center" prop="grade" sortable />
            <el-table-column label="当前价格" align="center" prop="currentPrice" sortable />
            <el-table-column label="预计涨幅" align="center" prop="planRise" sortable />
            <el-table-column label="预计跌幅" align="center" prop="planFall" sortable />
          </el-table>
        </div></el-col>
      </el-row>
    </el-main>
  </el-container>

</template>

<script>
import { listFinancePositionPlan, getFinancePositionPlan, addFinancePositionPlan, updateFinancePositionPlan, tradeAdviceList } from "@/api/finance/financePositionPlan";
import { listStockTrace, getStockTrace, delStockTrace, addStockTrace, updateStockTrace, tradeGradeList } from "@/api/finance/stockTrace";
import * as echarts from 'echarts';

export default {
  name: "FinancePositionPlan",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 交易建议数据
      tradeAdviceList: [],
      // 交易评分数据
      tradeGradeList: [],
      // 持仓计划数据
      listFinancePositionPlan: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        traceId: null,
        name: null,
        realityAmount: null,
        targetAmount: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询持仓计划列表 */
    getList() {
      this.loading = true;
      tradeAdviceList(this.queryParams).then(response => {
        this.tradeAdviceList = response.data;
        this.loading = false;
      });
      tradeGradeList(this.queryParams).then(response => {
        this.tradeGradeList = response.data;
        this.loading = false;
      });
      listFinancePositionPlan(this.queryParams).then(response => {
        this.listFinancePositionPlan = response.rows;
        this.loading = false;
        this.renderPositionBar();
        this.renderPositionPie();
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        traceId: null,
        name: null,
        realityAmount: null,
        targetAmount: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加持仓计划";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFinancePositionPlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFinancePositionPlan(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除持仓计划编号为"' + ids + '"的数据项？').then(function() {
        return delFinancePositionPlan(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有持仓计划数据项？').then(() => {
        this.exportLoading = true;
        return exportFinancePositionPlan(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    },
    /** 网格 */
    gotoGridding(row) {
      const id = row.traceId;
      this.$router.push("/stockTrace/gridding/stock/" + id);
    },
    dealAdvice({row, rowIndex}) {
      if ('BUY_DATE' === row.tradeAdviceType){
        return "buy-row";
      }
      if ('BUY_PRICE' === row.tradeAdviceType){
        return "buy-cautious-row";
      }
      if ('SELL_DATE' === row.tradeAdviceType){
        return "buy-danger-row";
      }
      if ('SELL_PRICE' === row.tradeAdviceType){
        return "buy-danger-row";
      }
      return "";
    },
    /** 饼图表渲染 **/
    renderPositionPie(){
      let chartDom = document.getElementById('positionPie');
      let myChart = echarts.init(chartDom);
      let listPositionPlan = this.listFinancePositionPlan;
      let titles = [];
      let pieData = [];
      for (let i = 0; i < listPositionPlan.length; i++) {
        const positionPlan = listPositionPlan[i];
        titles.push(positionPlan.name);
        pieData.push({ value: positionPlan.realityAmount, name: positionPlan.name },)
      }
      let option = {
        title: {
          text: '持仓比例',
          left: 'left'
        },
        //提示框组件,鼠标移动上去显示的提示内容
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}: {c} ({d}%)"//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。
        },
        series: [
          {
            name: '金额',
            type: 'pie',
            radius: '50%',
            data: pieData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      option && myChart.setOption(option);
    },
    /**
     * 持仓柱状图渲染
     */
    renderPositionBar(){
      var chartDom = document.getElementById('positionBar');
      var myChart = echarts.init(chartDom);
      var option;
      let listPositionPlan = this.listFinancePositionPlan;
      let barData = [['product', '实际持仓', '计划持仓']];

      let totle = 0;
      for (let i = 0; i < listPositionPlan.length; i++) {
        const positionPlan = listPositionPlan[i];
        totle = positionPlan.realityAmount + totle;
      }
      for (let i = 0; i < listPositionPlan.length; i++) {
        const positionPlan = listPositionPlan[i];
        barData.push([positionPlan.name, positionPlan.realityAmount*100/totle, positionPlan.targetAmount]);
      }
      option = {
        legend: {},
        tooltip: {},
        dataset: {
          source: barData
        },
        xAxis: { type: 'category' },
        yAxis: {},
        // Declare several bar series, each will be mapped
        // to a column of dataset.source by default.
        series: [{ type: 'bar' }, { type: 'bar' }]
      };

      option && myChart.setOption(option);
    }
  }
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
