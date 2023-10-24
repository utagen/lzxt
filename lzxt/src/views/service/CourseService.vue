<template>
  <section class="container course-service">
    <div class="breadcrumb-wrap">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>课程与服务</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <section class="item">
      <h2 class="item-header">道法题库</h2>
      <p class="text-center">给每位知识服务者都配上专属小程序</p>
      <div class="cover">
        <img src="@/images/covers/2.jpg" alt="按钮分析" />
      </div>
    </section>
    <section class="item">
      <h2 class="item-header">励志题库为新中考保驾护航</h2>
      <el-row :gutter="20">
        <el-col :sm="12" :xs="24" class="content">
          <h3 class="title first">超大题量</h3>
          <p>覆盖各大名校真题&nbsp;名师 储备题库</p>
          <h3 class="title">精准考点</h3>
          <p>万变不离其“点”精准考点创设百变题目</p>
          <h3 class="title">高效提速</h3>
          <p>刷题感除盲区&nbsp;最快最准确保证基础选择</p>
        </el-col>
        <el-col :sm="12" :xs="24" >
          <img class="cover" src="@/images/covers/1.jpg" alt="按钮分析" />
        </el-col>
      </el-row>
    </section>
  </section>
</template>

<style lang="scss" scoped>
@import "~@/styles/vars.scss";

.item {
  margin-top: 100px;
}

.item-header {
  font-size: $--font-size-extra-large * 1.5;
  text-align: center;
}

.text-center {
  color: $--color-text-regular;
  text-align: center;
}

.cover {
  text-align: center;

  img {
    max-width: 100%;
  }
}

.content {
  vertical-align: middle;

  h3 {
    margin-top: 45px;
    font-size: $--font-size-extra-large;
  }

  p {
    color: $--color-text-regular;
  }

}
</style>

<style lang="scss" scoped>
  @import "~@/styles/vars.scss";

  @media screen and (max-width: 768px) {
    .item {
      margin-top: 50px;
      padding: 0 10px;

      img { max-width: 100%;}
    }

    .content {
      padding: 0 20px!important;

      h3 {
        margin: 20px 0 10px 0;
      }
    }
  }

  @media screen and (max-width: 640px) {
    .item {
      margin-top: 20px;
    }
    .item-header {
      font-size: $--font-size-extra-large;
    }
  }

// @media screen and (max-width: 480px) {
//   .item-header {
//     font-size: $--font-size-large;
//   }
// }
</style>
