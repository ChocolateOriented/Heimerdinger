<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="追踪id" prop="traceId">
        <el-input
          v-model="queryParams.traceId"
          placeholder="请输入追踪id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="触发价格" prop="advicePrice">
        <el-input
          v-model="queryParams.advicePrice"
          placeholder="请输入触发价格"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网格持仓" prop="griddingAmount">
        <el-input
          v-model="queryParams.griddingAmount"
          placeholder="请输入网格持仓"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="网格持仓百分比" prop="griddingPercent">
        <el-input
          v-model="queryParams.griddingPercent"
          placeholder="请输入网格持仓百分比"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="触发日期" prop="adviceDate">
        <el-date-picker clearable size="small"
          v-model="queryParams.adviceDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择触发日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="定投持仓金额" prop="adviceAmount">
        <el-input
          v-model="queryParams.adviceAmount"
          placeholder="请输入定投持仓金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="定投持仓百分比" prop="advicePercent">
        <el-input
          v-model="queryParams.advicePercent"
          placeholder="请输入定投持仓百分比"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="交易类型" prop="tradeType">
        <el-select v-model="queryParams.tradeType" placeholder="请选择交易类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['finance:stockPositionPlan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:stockPositionPlan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['finance:stockPositionPlan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['finance:stockPositionPlan:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockPositionPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="追踪id" align="center" prop="traceId" />
      <el-table-column label="触发价格" align="center" prop="advicePrice" />
      <el-table-column label="网格持仓" align="center" prop="griddingAmount" />
      <el-table-column label="网格持仓百分比" align="center" prop="griddingPercent" />
      <el-table-column label="触发日期" align="center" prop="adviceDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.adviceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="定投持仓金额" align="center" prop="adviceAmount" />
      <el-table-column label="定投持仓百分比" align="center" prop="advicePercent" />
      <el-table-column label="交易类型" align="center" prop="tradeType" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['finance:stockPositionPlan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:stockPositionPlan:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改股票持仓计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
        </el-form-item>
        <el-form-item label="追踪id" prop="traceId">
          <el-input v-model="form.traceId" placeholder="请输入追踪id" />
        </el-form-item>
        <el-form-item label="触发价格" prop="advicePrice">
          <el-input v-model="form.advicePrice" placeholder="请输入触发价格" />
        </el-form-item>
        <el-form-item label="网格持仓" prop="griddingAmount">
          <el-input v-model="form.griddingAmount" placeholder="请输入网格持仓" />
        </el-form-item>
        <el-form-item label="网格持仓百分比" prop="griddingPercent">
          <el-input v-model="form.griddingPercent" placeholder="请输入网格持仓百分比" />
        </el-form-item>
        <el-form-item label="触发日期" prop="adviceDate">
          <el-date-picker clearable size="small"
            v-model="form.adviceDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择触发日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="定投持仓金额" prop="adviceAmount">
          <el-input v-model="form.adviceAmount" placeholder="请输入定投持仓金额" />
        </el-form-item>
        <el-form-item label="定投持仓百分比" prop="advicePercent">
          <el-input v-model="form.advicePercent" placeholder="请输入定投持仓百分比" />
        </el-form-item>
        <el-form-item label="交易类型" prop="tradeType">
          <el-select v-model="form.tradeType" placeholder="请选择交易类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStockPositionPlan, getStockPositionPlan, delStockPositionPlan, addStockPositionPlan, updateStockPositionPlan, exportStockPositionPlan } from "@/api/finance/stockPositionPlan";

export default {
  name: "StockPositionPlan",
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
      // 股票持仓计划表格数据
      stockPositionPlanList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        traceId: null,
        advicePrice: null,
        griddingAmount: null,
        griddingPercent: null,
        adviceDate: null,
        adviceAmount: null,
        advicePercent: null,
        tradeType: null
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
    /** 查询股票持仓计划列表 */
    getList() {
      this.loading = true;
      listStockPositionPlan(this.queryParams).then(response => {
        this.stockPositionPlanList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        advicePrice: null,
        griddingAmount: null,
        griddingPercent: null,
        adviceDate: null,
        adviceAmount: null,
        advicePercent: null,
        tradeType: null
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
      this.title = "添加股票持仓计划";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStockPositionPlan(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改股票持仓计划";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStockPositionPlan(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStockPositionPlan(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除股票持仓计划编号为"' + ids + '"的数据项？').then(function() {
        return delStockPositionPlan(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有股票持仓计划数据项？').then(() => {
        this.exportLoading = true;
        return exportStockPositionPlan(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
