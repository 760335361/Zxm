<template>
  <div class="role-management-page">
    <!-- 搜索表单区域 -->
    <div class="search-div">
      <el-form :model="queryDto" label-width="70px" size="small" inline>
        <el-form-item label="角色名称">
          <el-input
            v-model="queryDto.roleName"
            style="width: 200px"
            placeholder="请输入角色名称"
            clearable
          />
        </el-form-item>
        <el-form-item label="角色状态">
          <el-select
            v-model="queryDto.status"
            style="width: 120px"
            placeholder="请选择状态"
            clearable
          >
            <el-option label="正常" value="1" />
            <el-option label="禁用" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="searchSysRole">
            <el-icon><Search /></el-icon> 搜索
          </el-button>
          <el-button size="small" @click="resetData">
            <el-icon><Refresh /></el-icon> 重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 工具栏区域 -->
    <div class="tools-div">
      <el-button type="success" size="small" @click="handleAdd">
        <el-icon><Plus /></el-icon> 添 加
      </el-button>
    </div>

    <!-- 角色表格数据 -->
    <el-table
      v-loading="loading"
      element-loading-text="加载中..."
      :data="list"
      style="width: 100%"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" label="序号" width="60" />
      <el-table-column prop="roleName" label="角色名称" width="180" />
      <el-table-column prop="roleCode" label="角色标识" width="180" />
      <el-table-column prop="description" label="角色描述" />
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="180"
        :formatter="formatDate"
      />
      <el-table-column
        prop="status"
        label="状态"
        width="100"
        align="center"
      >
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="1"
            inactive-value="0"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="220">
        <template #default="scope">
          <el-button
            type="primary"
            size="small"
            icon="Edit"
            @click="handleEdit(scope.row)"
          >
            修改
          </el-button>
          <el-button
            type="danger"
            size="small"
            icon="Delete"
            @click="handleDelete(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <div class="pagination-div">
      <el-pagination
        current-page="pageParams.page"
        page-size="pageParams.limit"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="fetchData"
        @current-change="fetchData"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import {
  Search,
  Refresh,
  Plus,
  Edit,
  Delete,
  Check,
  Close,
} from '@element-plus/icons-vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { format } from 'date-fns'; // 引入日期格式化工具

// 引入API
import { GetSysRoleListByPage } from '@/api/sysRole';

// 响应式状态定义
const loading = ref(false); // 加载状态
const total = ref(0); // 总记录数
const list = ref([]); // 表格数据列表
const multipleSelection = ref([]); // 表格多选数据

// 分页参数
const pageParams = ref({
  page: 1, // 当前页码
  limit: 10, // 每页条数
});

// 查询条件
const queryDto = ref({
  roleName: '', // 角色名称
  status: '', // 角色状态
});

/**
 * @description: 页面加载时获取数据
 */
onMounted(() => {
  fetchData();
});

/**
 * @description: 加载角色列表数据
 */
const fetchData = async () => {
  try {
    loading.value = true;
    // 注意：这里调整了参数顺序，与API定义保持一致
    const { data } = await GetSysRoleListByPage(
      queryDto.value,
      pageParams.value.page,
      pageParams.value.limit
    );
    list.value = data.list;
    total.value = data.total;
  } catch (error) {
    console.error('获取角色列表失败:', error);
    ElMessage.error('获取角色列表失败，请稍后重试');
  } finally {
    loading.value = false;
  }
};

/**
 * @description: 搜索按钮点击事件
 */
const searchSysRole = () => {
  pageParams.value.page = 1; // 搜索时重置页码为第一页
  fetchData();
};

/**
 * @description: 重置搜索条件
 */
const resetData = () => {
  queryDto.value = {
    roleName: '',
    status: '',
  };
  searchSysRole(); // 重置后自动搜索
};

/**
 * @description: 表格多选事件
 * @param {Array} val - 选中的行数据
 */
const handleSelectionChange = (val) => {
  multipleSelection.value = val;
};

/**
 * @description: 新增角色
 */
const handleAdd = () => {
  // 这里可以打开新增角色的弹窗或跳转到新增页面
  console.log('新增角色');
  // 示例：
  // openRoleModal(); // 打开弹窗
  // router.push('/system/role/add'); // 跳转到新增页面
};

/**
 * @description: 编辑角色
 * @param {Object} row - 当前行数据
 */
const handleEdit = (row) => {
  console.log('编辑角色:', row);
  // 示例：
  // openRoleModal(row); // 打开弹窗并回显数据
  // router.push(`/system/role/edit/${row.id}`); // 跳转到编辑页面
};

/**
 * @description: 删除角色
 * @param {Object} row - 当前行数据
 */
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      '此操作将永久删除该角色, 是否继续?',
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
    );
    // 调用删除API
    // await DeleteSysRole(row.id);
    ElMessage.success('删除成功');
    fetchData(); // 重新加载数据
  } catch (error) {
    ElMessage.info('已取消删除');
  }
};

/**
 * @description: 角色状态切换
 * @param {Object} row - 当前行数据
 */
const handleStatusChange = async (row) => {
  console.log('切换角色状态:', row);
  try {
    // 调用更新状态API
    // await UpdateSysRoleStatus(row.id, row.status);
    ElMessage.success('状态更新成功');
  } catch (error) {
    console.error('更新角色状态失败:', error);
    ElMessage.error('状态更新失败，请稍后重试');
    // 失败时回滚状态
    row.status = row.status === '1' ? '0' : '1';
  }
};

/**
 * @description: 日期格式化
 * @param {string} date - 日期字符串
 * @returns {string} 格式化后的日期
 */
const formatDate = (date) => {
  if (!date) return '';
  return format(new Date(date), 'yyyy-MM-dd HH:mm:ss');
};
</script>

<style scoped>
.role-management-page {
  padding: 20px;
}

.search-div {
  margin-bottom: 16px;
  padding: 16px;
  border-radius: 4px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.tools-div {
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-div {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
</style>