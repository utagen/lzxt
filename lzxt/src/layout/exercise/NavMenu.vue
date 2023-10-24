<template>
  <el-menu router class="exercise-menu" :default-active="actived">
    <el-menu-item index="/courses">
      <i class="lzxt-icon lzxt-icon-computer"></i>
      <span>在线课程</span>
    </el-menu-item>
    <el-menu-item index="/purchased/courses">
      <i class="lzxt-icon lzxt-icon-book"></i>
      <span>我的课程</span>
    </el-menu-item>
    <el-menu-item index="/learned/courses">
      <i class="lzxt-icon lzxt-icon-study-fill"></i>
      <span>我的学习</span>
    </el-menu-item>
    <el-menu-item index="/wrong/topics">
      <i class="lzxt-icon lzxt-icon-wrong-notebook"></i>
      <span>错题本</span>
    </el-menu-item>
    <el-menu-item index="/orders">
      <i class="lzxt-icon lzxt-icon-order"></i>
      <span>订单管理</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'nav-menu',
  computed: {
    actived () {
      return this.$route.path
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.exercise-menu {
  padding-top: 130px;
  padding-bottom: 20px;
}

.el-menu-item {
  font-size: $--font-size-medium;
  padding-left: 30px;
  line-height: 64px;
  height: 64px;

  .lzxt-icon {
    margin: 0 10px 0 40px;
    font-size: 16px;
  }
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 1200px) {
  .exercise-menu {
    display: flex;
    padding:  0;
    margin: 0 auto;
    text-align: center;}

  .el-menu-item {
    flex: 1;
    text-align: center;
    padding: 0;
    .lzxt-icon { margin-left: 0;}
  }
}

@media screen and (max-width: 640px) and (min-width: 510px) {
  .el-menu-item .lzxt-icon {display: none;}
}
@media screen and (max-width: 510px) {
  .el-menu-item  span {display: none;}
}
</style>
