<template>
    <div class="wechat-qrcode">
      <div class="title">
        <i class="lzxt-icon lzxt-icon-wechat" v-if="showIcon"/>
        <span>{{title}}</span>
      </div>
      <qrcode :size="size" :url="url" @load="$emit('loaded')"></qrcode>
      <div class="footer"><slot name="footer"/></div>
    </div>
</template>

<script>
import Qrcode from './qrcode.vue'

export default {
  name: 'wechat-qrcode',
  components: { Qrcode },
  props: {
    url: {
      type: String,
      required: true
    },
    size: {
      type: Number,
      default: 200
    },
    showIcon: {
      type: Boolean,
      default: true
    },
    title: {
      type: String,
      default: '微信支付'
    },
    scaner: Function
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.wechat-qrcode {
  display: inline-block;
  padding: 0 20px;

  .title {
    text-align: center;
    line-height: 50px;
    font-size: $--font-size-medium;
  }

  .lzxt-icon {
    margin-right: 10px;
    color: $--color-success;
    font-size: $--font-size-medium;
  }

  .footer {
    font-size: $--font-size-extra-small;
    text-align: center;
  }
}
</style>
