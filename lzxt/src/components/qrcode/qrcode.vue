<template>
  <div class="qrcode" :style="style">
    <div :id="instanceId" ref="instance" style="display:none"></div>
    <img  ref="image"/>
  </div>
</template>

<script>
import QRCode from 'qrcodejs2'

export default {
  name: 'qrcode',
  props: {
    url: {
      type: String,
      default: 'about:blank'
    },
    size: {
      type: Number,
      default: 200
    },
    id: String
  },
  data () {
    return {
      instance: null,
      instanceId: this.id || this.$idBuilder('qr')
    }
  },
  computed: {
    style () {
      const { width, height } = this
      return { width, height }
    }
  },
  mounted () {
    this.instance = new QRCode(this.instanceId, {
      width: this.size,
      height: this.size,
      text: this.url, // 二维码地址
      colorDark: '#000',
      colorLight: '#fff'
    })
    this.emitEvent()
  },
  methods: {
    makeCode (value) {
      this.instance.clear()
      this.instance.makeCode(value)
      this.emitEvent()
    },
    emitEvent () {
      if (this.url !== 'about:blank') { this.$emit('load') }
      this.$refs.image.src = this.$refs.instance.querySelector('canvas').toDataURL('image/png')
    }
  },
  watch: {
    url: { handler: 'makeCode' }
  }

}
</script>
<style lang="scss" scoped>
.qrcode {
  display: inline-block;
  img { max-width: 100%;  max-height: 100%; vertical-align: top;}
}
</style>
