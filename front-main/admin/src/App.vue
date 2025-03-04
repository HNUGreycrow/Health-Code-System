<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>
<style >
.module{

  min-height: 30px;
  opacity: 1;
  border-radius: 22px;
  background: #FFFFFF;
  padding: 22px;
  margin-bottom: 22px;
  position: relative;

}

.module .title{
  opacity: 1;
  /** 文本1 */
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 0px;
  line-height: 18.75px;
  color: rgba(32, 32, 32, 1);
  text-align: left;
  vertical-align: top;
}
.el-table thead th{
  background-color: #F6F9FF!important;
}
.el-table{
  margin-bottom: 30px;
}

.el-pagination.is-background .el-pager li:not(.disabled).active{
  background-color: #476FDE;
}

.el-pagination.is-background .el-pager li:not(.disabled):hover{
  color: #476FDE;
}
.el-select .el-input.is-focus .el-input__inner,.el-input.is-active .el-input__inner, .el-input__inner:focus,.el-pagination__sizes .el-input .el-input__inner:hover{
  border-color: #476FDE;
}
.el-select-dropdown__item.selected{
  color: #476FDE;

}
.el-table th>.cell,.el-table .cell{
  font-size: 13px;
}
.el-button--primary{
  border-color: #476FDE;
  background-color: #476FDE;
}
.el-button--primary:focus, .el-button--primary:hover{
  opacity: 0.8;
    border-color: #476FDE;
  background-color: #476FDE;

}
.el-button--primary.is-active, .el-button--primary:active{
  opacity: 0.8;
    border-color: #476FDE;
  background-color: #476FDE;
}
.el-form-item{
  margin: 0;
}
</style>
