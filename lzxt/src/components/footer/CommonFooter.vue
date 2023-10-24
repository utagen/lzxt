<template>
  <footer class="footer">
    <section class="container footer-hyperlinks">
      <div>
        <el-row :gutter="25" class="qr-code">
          <el-col :span="12">
            <div class="img"><img src="@/images/qr-code/wechat-public-account.jpeg" /></div>
            <div class="name">关注官方公众号</div>
          </el-col>
          <el-col :span="12">
            <div class="img"><img src="@/images/qr-code/custom-service.jpeg" /></div>
            <div class="name">微信客服</div>
          </el-col>
        </el-row>
      </div>
      <!-- 延伸应用-->
      <dl>
        <dt>延伸应用</dt>
        <dd><a href="#" @click.prevent>延伸阅读</a></dd>
        <dd><a href="#" @click.prevent>知识付费</a></dd>
      </dl>
      <!-- 渠道合作 -->
      <dl>
        <dt>渠道合作</dt>
        <dd><a href="#" @click.prevent>合作伙伴</a></dd>
        <dd><a href="#" @click.prevent>渠道拓展</a></dd>
      </dl>
      <!-- 关于我们 -->
      <dl>
        <dt>走进学堂</dt>
        <dd><a href="#" @click.prevent>学堂简介</a></dd>
        <dd><a href="#" @click.prevent>加入我们</a></dd>
        <dd><a href="#" @click.prevent>服务条款</a></dd>
      </dl>
      <dl class="media-report">
        <dt>媒体报道</dt>
      </dl>
    </section>
    <section class="container container-copyright">
      Copyright@2018-2020 励志学堂 All Right Reserved.<a target="_blank" href="#" @click.prevent class="link">京ICP备20021610号</a>
    </section>
  </footer>
</template>

<script>
export default {
  name: 'common-footer'
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';
@import '~@/styles/mixins.scss';

footer {
  margin-top: 20px;
  padding: 80px 20px 0;
  background-color: #171e30;
  color: $--color-white;

  .container {
    padding: 10px 0;
  }

  a {
    color: $--color-white;
    opacity: .6;
  }
}

.footer-hyperlinks {
  display: flex;
  -webkit-box-pack: justify;
  -moz-box-pack: justify;
  justify-content: space-between;
  line-height: 40px;

  >div, >dl{
    margin-left: 50px\9;
    vertical-align: top\9;
    display: inline-block \9;
  }

  >div {
    margin-left: 0\9;
  }

  dl dd { margin-left: 0;}

  .qr-code {
    margin-top: 20px;
    font-size: $--font-size-extra-small;

    img { vertical-align: top; width: 110px; }

    .name { opacity: .6;  text-align: center;}
  }

}

.container-copyright {
  opacity: .3;
  text-align: center;
  font-size: $--font-size-extra-small;

  >div {
    vertical-align: top\9;
    display: inline-block\9;
    width: 50%\9;
  }

  a {opacity: 1;}

  img {
    vertical-align: top;
    margin-right: 5px;
  }

  .copyright {
    flex: 1;
    text-align: right;
  }

  .address {
    flex: 1;
    text-align: left;

    a {
      margin-left: 40px;

      &:first-child {
        margin-left: 0;
      }
    }
  }
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 800px) {
  .footer {
    padding-top: 20px;
    .footer-hyperlinks .qr-code {
      margin-top: 0;
    }
    .img  {
      text-align: center;
    }
  }
}
@media screen and (max-width: 600px) {
  .footer-hyperlinks {
    display: block;

    >div:first-child {
      width: 100%;
    }

    dl {
      vertical-align: top;
      display: inline-block;
      width: 25%;
      dd,dt {
        text-align: center;
      }
    }
  }

}
</style>
