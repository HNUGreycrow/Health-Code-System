<template>
  <div>
    <!-- 转码管理页面 -->
    <div class="module">
      <div class="tittle">用户信息列表</div>
      <div class="checkbox">
        <el-input v-model="input" placeholder="请输入身份证号" size="small" />
        <el-button type="primary" size="small" @click="search"> 查询</el-button>
      </div>

      <div v-loading="loading" class="table">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="uid" label="人员ID" align="center" />
          <el-table-column prop="name" label="姓名" align="center" />
          <el-table-column prop="identityCard" label="身份证号" align="center" />
          <el-table-column prop="status" label="状态" width="100px" align="center">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status == 0" type="success">绿码</el-tag>
              <el-tag v-if="scope.row.status == 1" type="warning">黄码</el-tag>
              <el-tag v-if="scope.row.healthCodeColor == 2" type="danger">红码</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="address" label="管理" width="300px" align="center">
            <template slot-scope="scope">
              <el-button size="small" type="success" plain @click="changeData(scope.row.uid, 0)">转绿码</el-button>
              <el-button size="small" type="warning" plain @click="changeData(scope.row.uid, 1)">转黄码</el-button>
              <el-button size="small" type="danger" plain @click="changeData(scope.row.uid, 2)">转红码</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  data() {
    return {
      input: '',
      loading: false,
      tableData: []
    }
  },
  methods: {
    /**
     * 查询用户信息接口
     */
    search() {
      request({
        url: '/health-code/health_code',
        method: 'get',
        params: {
          identity_card: this.input
        }
      }).then(res => {
        console.log(res)
        this.tableData = [res.data]
        console.log(this.tableData)
      })
    },
    /**
     * 对用户进行转码
     * @param uid 用户id
     * @param event 转码事件（0,1,2）
     */
    changeData(uid, event) {
      // 对用户进行转码
      request({
        url: '/health-code/transcodingEvents',
        method: 'post',
        data: {
          uid: this.tableData[0].uid,
          event: event
        }
      }).then((res) => {
        if (res.code === 200) {
          this.search()
          this.$message({
            message: '转码成功',
            type: 'success'
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.checkbox {
  margin: 20px 0;
  display: flex;
}

.checkbox>>>.el-input {
  width: 300px;
  margin-right: 50px;
}
</style>
