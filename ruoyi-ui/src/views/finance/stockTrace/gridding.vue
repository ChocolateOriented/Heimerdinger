<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="400px">
        <h3>追踪计划</h3>
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称"/>
          </el-form-item>
          <el-form-item label="代码" prop="code">
            <el-input v-model="form.code" placeholder="请输入代码"/>
          </el-form-item>
          <el-form-item label="成本价格" prop="costPrice">
            <el-input v-model="form.costPrice" placeholder="请输入成本价格"/>
          </el-form-item>
          <el-form-item label="持有逻辑" prop="traceLogicalType">
            <el-select v-model="form.traceLogicalType" placeholder="请选择持有逻辑">
              <el-option
                v-for="dict in dict.type.trace_logical_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="持有份额" prop="quotient">
            <el-input v-model="form.quotient" placeholder="请输入持有份额"/>
          </el-form-item>
          <el-form-item label="最小持有份额" prop="quotientMin">
            <el-input v-model="form.quotientMin" placeholder="请输入最小持有份额"/>
          </el-form-item>
          <el-form-item label="最大持有份额" prop="quotientMax">
            <el-input v-model="form.quotientMax" placeholder="请输入最大持有份额"/>
          </el-form-item>
          <el-form-item label="合理持有份额" prop="quotientFit">
            <el-input v-model="form.quotientFit" placeholder="请输入合理持有份额"/>
          </el-form-item>
          <el-form-item label="估值方式" prop="assessmentType">
            <el-select v-model="form.assessmentType" placeholder="请选择估值方式">
              <el-option
                v-for="dict in dict.type.assessment_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="成本估值指标" prop="assessmen">
            <el-input v-model="form.assessmen" placeholder="请输入成本估值指标"/>
          </el-form-item>
          <el-form-item label="最低估值指标" prop="assessmenMin">
            <el-input v-model="form.assessmenMin" placeholder="请输入预计最低估值指标"/>
          </el-form-item>
          <el-form-item label="最高估值指标" prop="assessmenMax">
            <el-input v-model="form.assessmenMax" placeholder="请输入预计最高估值指标"/>
          </el-form-item>
          <el-form-item label="合理估值指标" prop="assessmenFit">
            <el-input v-model="form.assessmenFit" placeholder="请输入预计合理估值指标"/>
          </el-form-item>
          <el-tooltip class="item" effect="dark" content="合理指标与最低指标之间百分比计算买入点" placement="top-start">
            <el-form-item label="安全边际" prop="safeSpan">
              <el-input v-model="form.safeSpan" placeholder="请输入安全边际, 合理指标与最低指标之间百分比计算买入点"/>
            </el-form-item>
          </el-tooltip>
          <el-form-item label="开始持有时间" prop="startTime">
            <el-date-picker clearable size="small"
                            v-model="form.startTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择开始持有时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="建仓截止时间" prop="keepData">
            <el-date-picker clearable size="small"
                            v-model="form.keepData"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择建仓截止时间">
            </el-date-picker>
          </el-form-item>
          <el-tooltip class="item" effect="dark" content="超过定投目标时间倍数, 控制风险避免快速建仓" placement="top-start">
            <el-form-item label="时间弹性" prop="timeSpan">
              <el-input v-model="form.timeSpan" placeholder="请输入时间弹性,超过定投目标范围"/>
            </el-form-item>
          </el-tooltip>
          <el-form-item label="计划id" prop="planId">
            <el-select v-model="form.planId" placeholder="请选择">
              <el-option
                v-for="item in planOptions"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                :disabled="item.delFlag == 2"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </el-aside>

      <el-container>
        <el-header height="160px">
          <el-descriptions
            class="margin-top"
            title="交易计划"
            :column="4"
            border
          >
            <el-descriptions-item label="市值">{{ current.price * form.quotient }}</el-descriptions-item>

            <el-descriptions-item label="交易次数">{{ plan.time }}</el-descriptions-item>
            <el-descriptions-item label="下跌触发">{{ plan.unit }}</el-descriptions-item>
            <!--            <el-descriptions-item label="交易金额">{{ plan.amount }}</el-descriptions-item>-->
            <el-descriptions-item label="定投日">周:
              <el-input v-model="plan.week" style="width: 4em" v-on:change="computePlan"/>
            </el-descriptions-item>
            <!--            <el-descriptions-item label="当前市净率">{{ current.pb }}</el-descriptions-item>-->
            <el-descriptions-item label="当前价格">{{ current.price }}</el-descriptions-item>
            <el-descriptions-item label="合理价格">{{ plan.priceFit }}</el-descriptions-item>
            <el-descriptions-item label="上涨空间">{{ plan.rise }}%</el-descriptions-item>
            <el-descriptions-item label="下跌空间">{{ plan.fall }}%</el-descriptions-item>
          </el-descriptions>
        </el-header>
        <el-main>
          <el-collapse v-model="activeNames">
            <el-collapse-item title="买入网格" name="1">
              <template>
                <el-table
                  :data="adviceTabel"
                  style="width: 100%"
                  max-height="600px"
                  :cell-class-name="dealAdvice"
                >
                  <el-table-column prop="advicePrice" label="触发价格" width="180"/>
                  <el-table-column prop="griddingAmount" label="网格持仓"/>
                  <el-table-column prop="griddingPercent" label="网格持仓百分比">
                    <template slot-scope="scope">
                      {{ scope.row.griddingPercent }}
                      <el-tag v-if="scope.row.realGriddingAmount" style="margin-left: 3em">
                        当前持仓
                      </el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column prop="adviceDate" label="触发日期" :formatter="dateFormat" width="180"/>
                  <el-table-column prop="adviceAmount" label="定投持仓"/>
                  <el-table-column prop="advicePercent" label="定投持仓百分比">
                    <template slot-scope="scope">
                      {{ scope.row.advicePercent }}
                      <el-tag v-if="scope.row.realAmount" style="margin-left: 3em">
                        当前持仓
                      </el-tag>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-collapse-item>
            <el-collapse-item title="分析记录" name="2">
              <el-row :gutter="24">
                <el-col :span="12">
                  <div style="line-height: 60px">
                    走势分析
                    <el-button @click="traceLogHandleAdd('TREND')">添加</el-button>
                    <a target="_blank" style="margin: 0px 10px;" :href="priceHref">行情</a>
                  </div>
                  <div style="max-height: 600px; overflow-y: scroll;">
                    <el-card class="box-card" v-for="trend in trendLogList" :key="trend.id">
                      <div style="line-height: 40px">{{ trend.createTime }}</div>
                      <div v-on:dblclick="traceLogHandleUpdate(trend.id)" style="line-height: 15px"
                           v-html="trend.content"></div>
                    </el-card>
                  </div>
                </el-col>

                <el-col :span="12">
                  <div style="line-height: 60px">
                    基本面分析
                    <el-button @click="traceLogHandleAdd('BASE')">添加</el-button>
                    <a target="_blank" :href="dongfangModeHref" style="margin: 0px 10px;" >杜邦分析</a>
                    <a target="_blank" :href="indexHref">财务指标</a>
                    <a target="_blank" :href="reportHref"  style="margin: 0px 10px;" >财务报告</a>
                    <a target="_blank" :href="shareholderHref">股东结构</a>
                  </div>
                  <div style="max-height: 600px; overflow-y:scroll;">
                    <el-card class="box-card" v-for="base in baseLogList" :key="base.id">
                      <div style="line-height: 40px">{{ base.createTime }}</div>
                      <div v-on:dblclick="traceLogHandleUpdate(base.id)" style="line-height: 15px"
                           v-html="base.content"></div>
                    </el-card>
                  </div>
                </el-col>
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </el-main>

      </el-container>
    </el-container>

    <!-- 添加或修改追踪日志对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="12 00px" append-to-body>
      <el-form ref="traceLogForm" :model="traceLogForm" label-width="80px">
        <el-form-item label="内容">
          <editor v-model="traceLogForm.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="股票编码" prop="code">
          <el-input v-model="traceLogForm.code" placeholder="请输入股票编码"/>
        </el-form-item>
        <el-form-item label="日志类型" prop="logType">
          <el-select v-model="traceLogForm.logType" placeholder="请选择日志类型">
            <el-option
              v-for="dict in dict.type.trace_log_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-card v-if="traceLogForm.logType == 'TREND'">
          <div v-html="trendReminderStr"></div>

        </el-card>
        <el-card v-if="traceLogForm.logType == 'BASE'" >
          <div v-html="baseReminderStr">
          </div>
        </el-card>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitTraceLogForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  listStockTrace,
  getStockTrace,
  delStockTrace,
  addStockTrace,
  updateStockTrace,
  exportStockTrace,
  findCurrentInfo
} from "@/api/finance/stockTrace";
import {saveStockPositionPlanList} from "@/api/finance/stockPositionPlan";
import {listFinancePositionPlan} from "@/api/finance/financePositionPlan";
import {baseReminder, trendReminder, baseDefault} from "@/api/finance/reminder";
import {
  listTraceLog,
  getTraceLog,
  delTraceLog,
  addTraceLog,
  updateTraceLog,
  exportTraceLog
} from "@/api/finance/traceLog";
import moment from "moment"

export default {
  name: "StockTraceGridding",
  dicts: ['trace_logical_type', 'assessment_type', 'trace_log_type'],
  data() {
    return {
      // 是否显示弹出层
      open: false,
      // 遮罩层
      form: {},
      traceLogForm: {content: baseDefault()},
      activeNames: ['1', '2'],
      plan: {
        week: 4,
      },
      loading: true,
      adviceTabel: [],
      baseLogList: [],
      trendLogList: [],
      current: {},
      financePositionPlan: null,
      sourceFinancePositionPlanId: 5,
      sourceFinancePositionPlan: {},
      targetFinancePositionPlan: {},
      // 计划下拉
      planOptions: [],
      maxAmount: 0,
      thinCode: "",
      indexHref: "",
      reportHref: "",
      dongfangModeHref: "",
      priceHref: "",
      shareholderHref: "",
      title:"",
      baseReminderStr:baseReminder(),
      trendReminderStr:trendReminder(),
    };
  },
  created() {
    // 加载计划选择列表
    listFinancePositionPlan().then(response => {
      this.planOptions = response.rows;
    });

    const id = this.$route.params && this.$route.params.id;
    let that = this;
    if (id) {
      this.loading = true;
      getStockTrace(id).then((response) => {
        that.form = response.data;
        if (that.form.code == null) {
          return;
        }
        that.thinCode = that.form.code.toLowerCase().replace("sh", "").replace("sz", "");
        that.indexHref = "https://vip.stock.finance.sina.com.cn/corp/go.php/vFD_FinanceSummary/stockid/" + that.form.code + "/displaytype/4.phtml?source=gjzb";
        that.dongfangModeHref = "https://emweb.securities.eastmoney.com/pc_hsf10/pages/index.html?type=web&code="+ that.form.code +"&color=b#/cwfx/dbfx";
        that.priceHref = "https://finance.sina.com.cn/realstock/company/" + that.form.code + "/nc.shtml";
        // that.reportHref = "https://vip.stock.finance.sina.com.cn/corp/go.php/vCB_Bulletin/stockid/"+ that.form.code +"/page_type/ndbg.phtml";
        that.reportHref = "https://vip.stock.finance.sina.com.cn/corp/go.php/vCB_Bulletin/stockid/"+ that.thinCode +"/page_type/ndbg.phtml";
        that.shareholderHref = "https://emweb.securities.eastmoney.com/pc_hsf10/pages/index.html?type=web&code=" + that.form.code + "&color=b#/gdyj";
        findCurrentInfo({"code": that.form.code}).then((response) => {
          that.current = response.data;
          that.computePlan();
        })
        this.getlistTraceLog();
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
          this.computePlan();
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
    getDate(startDate, endDate, weak) {
      let dates = [];
      let dateTime = new Date(startDate);
      let endDateTime = new Date(endDate);
      while (dateTime < endDateTime) {
        dateTime.setDate(dateTime.getDate() + 1);
        let day = dateTime.getDay();
        if (day === weak) {
          dates.push(new Date(dateTime));
        }
      }
      return dates;
    },
    dateFormat: function (row, column) {
      let date = row[column.property];
      if (date === undefined) {
        return ''
      }
      return moment(date).format("YYYY-MM-DD")
    },
    floatFormat(number) {
      return Math.floor(number * 1000) / 1000;
    },
    computePlan() {
      let that = this;

      that.plan.tradeDate = that.getDate(that.form.startTime, that.form.keepData, that.plan.week);
      that.plan.time = that.plan.tradeDate.length;
      that.plan.amount = that.floatFormat(that.form.quotientFit / that.plan.time);
      that.plan.priceFit = that.floatFormat(that.form.costPrice * that.form.assessmenFit / that.form.assessmen);
      that.plan.priceMin = that.floatFormat(that.form.costPrice * that.form.assessmenMin / that.form.assessmen);
      //安全价格
      that.plan.priceSafe = that.plan.priceMin + (that.plan.priceFit - that.plan.priceMin) * (100 - that.form.safeSpan) / 100
      that.plan.unit = (that.plan.priceSafe - that.plan.priceMin) / that.plan.time;

      that.computeTable();

      that.plan.rise = that.floatFormat((that.plan.priceFit - that.current.price) * 100 / that.current.price);
      that.plan.fall = that.floatFormat((that.current.price - that.plan.floorPrice) * 100 / that.current.price);

    },
    computeTable() {
      if (this.form.code == null) {
        return;
      }
      let that = this;
      this.loading = false;
      //获取当前股票信息
      //定投计划
      let floorPrice = 0;
      that.adviceTabel = [];
      for (let i = 0; i < that.plan.tradeDate.length; i++) {
        // debugger
        let advicePrice = that.floatFormat(Number(that.plan.priceSafe) - i * that.plan.unit);
        let adviceAmount = that.floatFormat((i + 1) * that.plan.amount);
        let advicePercent = that.floatFormat(adviceAmount * 100 / Number(that.form.quotientFit));
        let griddingPercent = that.floatFormat((i + 1) * 100 / that.plan.tradeDate.length)
        let griddingAmount = (griddingPercent * that.form.quotientFit) / 100;
        floorPrice = advicePrice;
        that.adviceTabel.push({
          "traceId": that.form.id,
          "adviceDate": that.plan.tradeDate[i],
          "advicePrice": advicePrice,
          "adviceAmount": adviceAmount,
          "advicePercent": advicePercent,
          "griddingAmount": griddingAmount,
          "griddingPercent": griddingPercent,
        });
      }
      that.plan.floorPrice = floorPrice;

      //定投计划风险分级
      let buyNum = 0;
      let realityAmount = that.form.quotient;
      let realityAmountIndex = null;
      let realityGriddingAmountIndex = null;

      for (let j = 0; j < that.adviceTabel.length; j++) {
        const advice = that.adviceTabel[j];
        advice.hadBuy = "";

        if (realityAmount >= advice.adviceAmount) {
          realityAmountIndex = j;
          advice.hadBuy = "hadBuy";
        }
        if (realityAmount >= advice.griddingAmount) {
          realityGriddingAmountIndex = j;
          advice.griddingHadBuy = "hadBuy";
        }
        if (new Date() > advice.adviceDate) {
          //建议买入
          advice.dateCss = "buy-row";
          buyNum++;
          continue;
        }
        let buyNumTimeSpan = buyNum * that.form.timeSpan;
        if (j < buyNumTimeSpan || advice.advicePrice >= this.current.price) {
          //超过定投目标时间弹性倍数后建议卖出
          if (j < buyNumTimeSpan && advice.advicePrice >= this.current.price) {
            //建议持有
            advice.priceCss = 'buy-row';
            continue;
          }
          if (advice.advicePrice >= this.current.price){
            //建议持有
            advice.priceCss = 'buy-cautious-row';
            continue;
          }
        } else if(realityAmount >= advice.adviceAmount){
          //建议卖出
          advice.priceCss = 'buy-danger-row';
          continue;
        }
      }
      if (realityAmountIndex != null) {
        that.adviceTabel[realityAmountIndex].realAmount = true;
      }
      if (realityGriddingAmountIndex != null) {
        that.adviceTabel[realityGriddingAmountIndex].realGriddingAmount = true;
      }

    },
    dealAdvice({row, column, rowIndex, columnIndex}) {
      if (column.property == "advicePrice" || column.property == "griddingAmount" || column.property == "griddingPercent") {
        if (row.priceCss != null) {
          return row.priceCss;
        }
        return row.griddingHadBuy;
      }
      if (column.property == "adviceDate" || column.property == "adviceAmount" || column.property == "advicePercent") {
        if (row.dateCss != null) {
          return row.dateCss;
        }
        return row.hadBuy;
      }
      return "";
    },
    handelPosition(row) {
      this.targetFinancePositionPlan.realityAmount = row.adviceAmount;
      this.positionChange(this.sourceFinancePositionPlanId);
      this.open = true;
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    /** 新增按钮操作 */
    traceLogHandleAdd(logType) {
      let defultContent = "";
      if(logType == "BASE"){
        defultContent = baseDefault();
      }
      this.traceLogFormReset(defultContent);
      this.traceLogForm.code = this.form.code;
      this.traceLogForm.logType = logType;
      this.open = true;
      this.title = "添加追踪日志";
    },
    // 表单重置
    traceLogFormReset(defultContent) {
      this.traceLogForm = {
        id: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        content: defultContent,
        code: null,
        logType: null
      };
      this.resetForm("traceLogForm");
    },
    /** 修改按钮操作 */
    traceLogHandleUpdate(id) {
      getTraceLog(id).then(response => {
        this.traceLogForm = response.data;
        this.open = true;
        this.title = "修改追踪日志";
      });
    },
    getlistTraceLog() {
      listTraceLog({"code": this.form.code, "logType": "BASE"}).then((response) => {
        this.baseLogList = response.rows;
      })
      listTraceLog({"code": this.form.code, "logType": "TREND"}).then((response) => {
        this.trendLogList = response.rows;
      })
    },
    submitTraceLogForm() {
      this.$refs["traceLogForm"].validate(valid => {
        if (valid) {
          if (this.traceLogForm.id != null) {
            updateTraceLog(this.traceLogForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getlistTraceLog();
            });
          } else {
            addTraceLog(this.traceLogForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getlistTraceLog();
            });
          }
        }
      });
    },
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
