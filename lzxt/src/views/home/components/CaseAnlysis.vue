<template>
  <section class="case-anlysis">
    <h2 class="header">励志题库为新中考保驾护航</h2>
    <el-row :gutter="20">
      <el-col :sm="12" :xs="24" class="content">
        <h3 class="title first">超大题量</h3>
        <p>覆盖各大名校真题&nbsp;名师 储备题库</p>
        <h3 class="title">精准考点</h3>
        <p>万变不离其“点”精准考点创设百变题目</p>
        <h3 class="title">高效提速</h3>
        <p>刷题感除盲区&nbsp;最快最准确保证基础选择</p>
        <div class="footer">
          <el-button type="primary" @click="$router.push('/courseAndService')">了解详情</el-button>
        </div>
      </el-col>
      <el-col :sm="12" :xs="24">
        <img class="cover" src="@/images/covers/2.jpg" alt="按钮分析"/>
      </el-col>
    </el-row>
  </section>
</template>

<script>
export default {
  name: 'case-anlysis'
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.header {
  font-size: $--font-size-extra-large * 1.5;
  text-align: center;
}

.content {
  h3 {
    margin-top: 45px;
    font-size: $--font-size-extra-large;
  }

  p {
    color: $--color-text-regular;
  }

  .footer {
    margin-top: 20px;
    text-align: right;
  }
}
.cover { max-width: 100%;}
</style>

<style lang="scss" scoped>
  @import '~@/styles/vars.scss';

  @media screen and (max-width: 768px) {
    .header {
      font-size: $--font-size-extra-large ;
    }

    .content {
      padding: 0 20px !important;
      h3 {
        font-size: $--font-size-large;
        margin: 20px 0 10px;
      }

      .footer { text-align:  left;}
    }

    .case-anlysis img {max-width: 100%;}
  }
</style>
