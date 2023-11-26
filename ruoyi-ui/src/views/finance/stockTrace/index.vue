<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="代码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计划id" prop="planId">
        <el-input
          v-model="queryParams.planId"
          placeholder="请输入计划id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['finance:stockTrace:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['finance:stockTrace:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['finance:stockTrace:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['finance:stockTrace:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockTraceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id"/>
      <el-table-column label="名称" align="center" prop="name"/>
      <el-table-column label="代码" align="center" prop="code"/>
      <el-table-column label="成本价格" align="center" prop="costPrice"/>
      <el-table-column label="持有份额" align="center" prop="quotient"/>
      <el-table-column label="最小持有份额" align="center" prop="quotientMin"/>
      <el-table-column label="最大持有份额" align="center" prop="quotientMax"/>
      <el-table-column label="合理持有份额" align="center" prop="quotientFit"/>
      <el-table-column label="估值方式" align="center" prop="assessmentType"/>
      <el-table-column label="成本估值指标" align="center" prop="assessmen"/>
      <el-table-column label="预计最低估值指标" align="center" prop="assessmenMin"/>
      <el-table-column label="预计最高估值指标" align="center" prop="assessmenMax"/>
      <el-table-column label="预计合理估值指标" align="center" prop="assessmenFit"/>
      <el-table-column label="安全边际, 合理指标与最低指标之间百分比计算买入点" align="center" prop="safeSpan"/>
      <el-table-column label="开始持有时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="目标持有时间" align="center" prop="keepData" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.keepData, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时间弹性,超过定投目标范围" align="center" prop="timeSpan"/>
      <el-table-column label="计划id" align="center" prop="planId"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="gotoGridding(scope.row)"
          >网格
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:stockTrace:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改股票追踪对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入代码"/>
        </el-form-item>
        <el-form-item label="成本价格" prop="costPrice">
          <el-input v-model="form.costPrice" placeholder="请输入成本价格"/>
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
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="成本估值指标" prop="assessmen">
          <el-input v-model="form.assessmen" placeholder="请输入成本估值指标"/>
        </el-form-item>
        <el-form-item label="预计最低估值指标" prop="assessmenMin">
          <el-input v-model="form.assessmenMin" placeholder="请输入预计最低估值指标"/>
        </el-form-item>
        <el-form-item label="预计最高估值指标" prop="assessmenMax">
          <el-input v-model="form.assessmenMax" placeholder="请输入预计最高估值指标"/>
        </el-form-item>
        <el-form-item label="预计合理估值指标" prop="assessmenFit">
          <el-input v-model="form.assessmenFit" placeholder="请输入预计合理估值指标"/>
        </el-form-item>
        <el-form-item label="安全边际, 合理指标与最低指标之间百分比计算买入点" prop="safeSpan">
          <el-input v-model="form.safeSpan" placeholder="请输入安全边际, 合理指标与最低指标之间百分比计算买入点"/>
        </el-form-item>
        <el-form-item label="开始持有时间" prop="startTime">
          <el-date-picker clearable size="small"
                          v-model="form.startTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择开始持有时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="目标持有时间" prop="keepData">
          <el-date-picker clearable size="small"
                          v-model="form.keepData"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择目标持有时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="时间弹性,超过定投目标范围" prop="timeSpan">
          <el-input v-model="form.timeSpan" placeholder="请输入时间弹性,超过定投目标范围"/>
        </el-form-item>
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
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
  exportStockTrace
} from "@/api/finance/stockTrace";
import {listFinancePositionPlan} from "@/api/finance/financePositionPlan";

export default {
  name: "StockTrace",
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
      // 股票追踪表格数据
      stockTraceList: [],
      // 计划下拉
      planOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        code: null,
        costPrice: null,
        quotient: null,
        quotientMin: null,
        quotientMax: null,
        quotientFit: null,
        assessmentType: null,
        assessmen: null,
        assessmenMin: null,
        assessmenMax: null,
        assessmenFit: null,
        safeSpan: null,
        startTime: null,
        keepData: null,
        timeSpan: null,
        planId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    // 加载计划选择列表
    listFinancePositionPlan().then(response => {
      this.planOptions = response.rows;
    });
  },
  methods: {
    /** 网格 */
    gotoGridding(row) {
      const id = row.id;
      this.$router.push("/stockTrace/gridding/stock/" + id);
    },
    /** 查询股票追踪列表 */
    getList() {
      this.loading = true;
      listStockTrace(this.queryParams).then(response => {
        this.stockTraceList = response.rows;
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
        name: null,
        code: null,
        costPrice: null,
        quotient: null,
        quotientMin: null,
        quotientMax: null,
        quotientFit: null,
        assessmentType: null,
        assessmen: null,
        assessmenMin: null,
        assessmenMax: null,
        assessmenFit: null,
        safeSpan: null,
        startTime: null,
        keepData: null,
        timeSpan: null,
        planId: null
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
      const id = row.id;
      this.$router.push("/stockTrace/gridding/stock/" + id);

      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 多选框选中数据
    handlegotoGridding(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加股票追踪";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStockTrace(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改股票追踪";
      });
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除股票追踪编号为"' + ids + '"的数据项？').then(function () {
        return delStockTrace(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有股票追踪数据项？').then(() => {
        this.exportLoading = true;
        return exportStockTrace(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {
      });
    }
  }
};
</script>
