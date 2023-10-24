<template>
  <el-menu router mode="horizontal" class="nav-menu" text-color="#333333" active-text-color="#B674F3" unique-opened @select="hanleMenuSelect">
    <el-menu-item index="/courseAndService" >课程与服务</el-menu-item>
    <!--<el-submenu index="courseService" class="nav-menu" popper-class="nav-sub-menu">
      <template slot="title">课程与服务</template>
      <el-submenu index="multiPlatformCouse">
        <template slot="title" index="platform">多终端课堂</template>
        <el-menu-item index="miniProgramCourse">
          <span>小程序课堂</span>
        </el-menu-item>
        <el-menu-item index="miniProgram">
          <span>小程序</span>
        </el-menu-item>
        <el-menu-item index="app">
          <span>APP</span>
        </el-menu-item>
      </el-submenu>
      <el-submenu index="exercise">
        <template slot="title">习题练习</template>
        <el-menu-item index="history">
          <span>历史</span>
        </el-menu-item>
        <el-menu-item index="chemistry">
          <span>化学</span>
        </el-menu-item>
      </el-submenu>
    </el-submenu> -->
    <el-menu-item index="/cases">案例与分析</el-menu-item>
  </el-menu>
</template>

<script>
export default {
  name: 'nav-menu',
  methods: {
    hanleMenuSelect (key, keyPath) {
      console.log(key, keyPath)
    }
  }
}
</script>
<style lang="scss">
  .el-menu.el-menu--horizontal {
    border-bottom: none;
  }
</style>

<style lang="scss">
@import '~@/styles/vars.scss';

.nav-menu {
  font-size: $--font-size-extra-large;

  &.el-menu--horizontal {
    > .el-menu-item {
      font-size: $--font-size-extra-large;
      height: 74px;
    }
  }
}
</style>

<style lang="scss">
@import '~@/styles/vars.scss';
@media screen and (max-width: 790px) {
  .nav-menu {
    &.el-menu--horizontal > .el-menu-item {
      font-size: $--font-size-large;
    }
  }
}

@media screen and (max-width: 768px) {
  .nav-menu {
    display: flex;
    text-align: center;
    &.el-menu--horizontal > .el-menu-item {
      flex: 1;
      line-height: 74px;
    }
  }
}

@media screen and (max-width: 499px) {
  .nav-menu {
    height: 50px;
    &.el-menu--horizontal > .el-menu-item {
    flex: 1;
    padding: 0;
    font-size: $--font-size-base;
    line-height: 50px;
    height: 50px;
  }
  }
}
</style>
