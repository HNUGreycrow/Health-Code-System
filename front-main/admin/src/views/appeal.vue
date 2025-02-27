<template>
  <div class="module">
    <!-- 健康码申诉页面 -->
    <div class="tittle">申诉信息列表</div>
    <div v-loading="reverseloading" class="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="appealId" label="申诉信息ID" width="100px" align="center" />
        <el-table-column prop="userName" label="姓名" align="center" />
        <el-table-column prop="identityCard" label="身份证号" align="center" />
        <el-table-column prop="appealReason" label="理由" align="center" />
        <el-table-column label="材料图片" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="saveImg(scope.row)">下载</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="healthCodeColor" label="健康码颜色" width="100px" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.healthCodeColor == 0" type="success">绿码</el-tag>
            <el-tag v-if="scope.row.healthCodeColor == 1" type="warning">黄码</el-tag>
            <el-tag v-if="scope.row.healthCodeColor == 2" type="danger">红码</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="appealStatus" label="状态" width="100px" align="center">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.appealStatus == 0" type="warning">未处理</el-tag>
            <el-tag v-if="scope.row.appealStatus == 1" type="success">已处理</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="管理" width="300px" align="center">
          <template slot-scope="scope">
            <el-button size="small" type="success" plain @click="changeData(scope.row.appealId, 0)">转绿码</el-button>
            <el-button size="small" type="warning" plain @click="changeData(scope.row.appealId, 1)">转黄码</el-button>
            <el-button size="small" type="danger" plain @click="changeData(scope.row.appealId, 2)">转红码</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          background
          :current-page="currentPage4"
          :page-sizes="[100, 200, 300, 400]"
          :page-size="100"
          layout="total, sizes, prev, pager, next, jumper"
          :total="10000"
        >
        </el-pagination> -->
    </div>
  </div>

</template>

<script>
import request from '@/utils/request'
export default {
  data() {
    return {
      reverseloading: false,
      tableData: []
    }
  },
  created() {
    this.getData()
  },
  methods: {
    saveImg(row) {
      var base64 = row.appealMaterials.toString() // base64
      var byteCharacters = atob(
        base64.replace(/^data:image\/(png|jpeg|jpg);base64,/, '')
      )
      var byteNumbers = new Array(byteCharacters.length)
      for (var i = 0; i < byteCharacters.length; i++) {
        byteNumbers[i] = byteCharacters.charCodeAt(i)
      }
      var byteArray = new Uint8Array(byteNumbers)
      var blob = new Blob([byteArray], {
        type: undefined
      })
      var aLink = document.createElement('a')
      aLink.download = row.userName + '_健康码申诉.jpg' // 这里写保存时的图片名称
      aLink.href = URL.createObjectURL(blob)
      aLink.click()
    },
    /**
     * 获取申诉信息数据
     */
    getData() {
      this.reverseloading = true
      request({
        url: '/health-code/appeal',
        method: 'get'
      })
        .then((res) => {
          this.reverseloading = false
          this.tableData = res.data
        })
        .catch((res) => {
          console.log(res)
          this.reverseloading = false
        })
    },
    /**
     * 处理申诉并对用户进行转码
     * @param appealId 处理的申诉id
     * @param event 转码事件（0,1,2）
     */
    changeData(appealId, event) {
      request({
        url: '/health-code/appeal/review',
        method: 'post',
        data: {
          appealId: appealId,
          healthCodeChangeEvent: event
        }
      }).then(res => {
        if (res.code === 200) {
          this.getData()
          this.$message({
            message: '转码成功',
            type: 'success'
          })
        } else {
          this.$message.error('error')
        }
      })
    }
  }
}

</script>

<style scoped>
.table {
  margin: 20px 0;
}

.form {
  display: flex;
  justify-content: left;
  width: 900px;
  flex-wrap: wrap;

  /* float: left; */
}

.form>>>.el-input {
  width: 200px;
  margin-right: 50px;
  margin-top: 20px;
}
</style>
