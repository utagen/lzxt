<template>
  <section class="share-wrap">
    <div class="logo"><img src="@/images/logo.png" /></div>
    <div class="content">
      <p>欢迎来到励志学堂！</p>
      <p>请在电脑上访问&nbsp;<code>www.lzxtedu.com</code>&nbsp;并使用该微信号登录进行学习</p>
    </div>
    <div class="account-button" v-if="$weixin">
      <a class="account-link" href="https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzAxNDE0OTE0MQ==#wechat_redirect">进入公众号</a>
    </div>
  </section>
</template>

<script>
export default { name: 'share' }
</script>

<style lang="scss" scoped>
  @import '~@/styles/vars.scss';

  .share-wrap {
    padding: 20px;
  }

  .logo {
    width: 40%;
    margin: 80px auto 20px;
    text-align: center;

    img {max-width: 100%;}
  }

  .content {
    text-align: center;
    line-height: 2;
    color: $--color-text-regular;
    font-size: $--font-size-base;
  }

  .account-button {
    margin-top: 50px;
    text-align: center;
  }

  .account-link {
    padding: 12px 20px;
    border: 1px solid $--color-success;
    color: $--color-success;
    border-radius: $--border-radius-base;
  }

</style>
