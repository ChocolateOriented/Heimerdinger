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
      <el-form-item label="市净率" prop="pb">
        <el-input
          v-model="queryParams.pb"
          placeholder="请输入市净率"
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
          v-hasPermi="['finance:stockTrace:edit']"
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
          v-hasPermi="['finance:stockTrace:remove']"
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
          v-hasPermi="['finance:stockTrace:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockTraceList" @selection-change="handleSelectionChange" @row-dblclick="gotoGridding">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="合理持有金额" align="center" prop="id" />
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="代码" align="center" prop="code" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="市净率" align="center" prop="pb" />
      <el-table-column label="预计最低市净率" align="center" prop="pbMin" />
      <el-table-column label="预计最高市净率" align="center" prop="pbMax" />
      <el-table-column label="预计合理市净率" align="center" prop="pbFit" />
      <el-table-column label="持有时间" align="center" prop="keepData" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.keepData, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="持有金额" align="center" prop="amount" />
      <el-table-column label="最小持有金额" align="center" prop="amountMin" />
      <el-table-column label="最大持有金额" align="center" prop="amountMax" />
      <el-table-column label="合理持有金额" align="center" prop="amountFit" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="gotoGridding(scope.row)"
          >网格</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['finance:stockTrace:remove']"
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

    <!-- 添加或修改股票追踪对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入代码" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="市净率" prop="pb">
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
        <el-form-item label="持有时间" prop="keepData">
          <el-date-picker clearable size="small"
            v-model="form.keepData"
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStockTrace, getStockTrace, delStockTrace, addStockTrace, updateStockTrace, exportStockTrace } from "@/api/finance/stockTrace";

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
        price: null,
        pb: null,
        pbMin: null,
        pbMax: null,
        pbFit: null,
        keepData: null,
        amount: null,
        amountMin: null,
        amountMax: null,
        amountFit: null
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
        name: null,
        code: null,
        price: null,
        pb: null,
        pbMin: null,
        pbMax: null,
        pbFit: null,
        keepData: null,
        amount: null,
        amountMin: null,
        amountMax: null,
        amountFit: null
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

      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 多选框选中数据
    handlegotoGridding(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
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
      this.$modal.confirm('是否确认删除股票追踪编号为"' + ids + '"的数据项？').then(function() {
        return delStockTrace(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
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
      }).catch(() => {});
    }
  }
};
</script>
