<template>
  <div class="quick-service">
    <!--<div class="quick-service-item">
      <a href="#" @click.prevent><i class="lzxt-icon lzxt-icon-phone"/></a>
    </div> -->
    <div class="quick-service-item">
      <a href="#" @click.prevent><i class="lzxt-icon lzxt-icon-customer-service"/></a>
    </div>
    <div class="quick-service-item">
      <el-popover placement="right" trigger="hover">
        <div class="qr-code"><img src="@/images/qr-code/wechat-public-account.jpeg" width="150"/></div>
        <a slot="reference" href="#" @click.prevent><i class="lzxt-icon lzxt-icon-wechat"/></a>
      </el-popover>
    </div>
    <div class="quick-service-item">
      <el-popover placement="right" trigger="hover">
        <div><img src="@/images/qr-code/custom-service.jpeg" width="150"/></div>
        <a slot="reference" href="#" @click.prevent><i class="lzxt-icon lzxt-icon-users"/></a>
      </el-popover>
    </div>
    <!--<div class="quick-service-item">
      <a href="#" @click.prevent><i class="lzxt-icon lzxt-icon-weibo"/></a>
    </div>-->
  </div>
</template>

<script>
export default {
  name: 'quick-service'
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.quick-service {
  position: fixed;
  width: 43px;
  right: 50px;
  top: 50%;
  z-index: 3000;
  transform:translateY(-50%);
  background-color: $--color-primary-light-2;
  border-left: 1px solid $--color-primary-light-2;
  border-right: 1px solid $--color-primary-light-2;

  i {
    border-top: 1px solid $--border-color-light;
    color: $--color-white;
    display: block;
    height: 45px;
    line-height: 45px;
    font-size: 20px;
    text-align: center;
  }

  .quick-service-item:first-child i{
    border-top: none;
  }
}

</style>

<style lang="scss" scoped>
@media screen and (max-width: 768px) {
  .quick-service {
    right: 20px;
  }
}

@media screen and (max-width: 640px) {
  .quick-service {
    right: 10px;
  }
}
@media screen and (max-width: 640px) {
  .quick-service {
    right: 10px;
  }
}

@media screen and (max-width: 480px) {
  .quick-service {
    right: 0;
  }
}
</style>
