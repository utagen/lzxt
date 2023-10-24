<template>
  <div class="poster">
    <img class="poster-image" :src="imageUrl" alt="poster"/>
    <qrcode ref="qrcode" :size="qrcodeSize" class="qrcode hidden" :url="url" @load="onQRcodeLoaded"></qrcode>
    <canvas ref="canvas" :width="width" :height="height" class="hidden"/>
    <img ref="backgournd" src="@/images/covers/poster.png" @load="onBackgounrdLoaded" class="hidden"/>
  </div>
</template>

<script>
import Qrcode from '../qrcode/qrcode.vue'

export default {
  name: 'poster',
  props: {
    width: {
      type: Number,
      default: 600
    },
    height: {
      type: Number,
      default: 900
    },
    url: {
      type: String,
      required: true
    },
    qrcodeSize: {
      type: Number,
      default: 95
    }
  },
  components: { Qrcode },
  data () {
    return {
      qrcodeId: this.$idBuilder('poster'),
      backgroundId: this.$idBuilder('bg'),
      qrcodeLoaded: false,
      backgroundLoaded: false,
      imageUrl: ''
    }
  },

  methods: {
    onQRcodeLoaded () {
      this.qrcodeLoaded = true
      this.startPaint()
    },
    onBackgounrdLoaded () {
      this.backgroundLoaded = true
      this.startPaint()
    },
    startPaint () {
      if (this.backgroundLoaded && this.qrcodeLoaded) {
        const { backgournd, qrcode, canvas } = this.$refs
        this.imageUrl = this.drawPoster(canvas, backgournd, qrcode.$el.querySelector('img'))
      }
    },
    drawPoster (canvas, background, qrcode) {
      const ctx = canvas.getContext('2d')
      const { width, height, qrcodeSize } = this
      const offset = { left: 32, bottom: 26 }
      ctx.drawImage(background, 0, 0, width, height)

      ctx.fillStyle = '#ffffff'
      ctx.fillRect(offset.left, height - offset.bottom - qrcodeSize - 10, qrcodeSize + 10, qrcodeSize + 10)

      ctx.drawImage(qrcode, offset.left + 5, height - offset.bottom - qrcodeSize - 5, qrcodeSize, qrcodeSize)

      return canvas.toDataURL()
    }
  }
}
</script>

<style lang="scss" scoped>
.poster {
  position: relative;
  height: 100%;
  width: 100%;

  &-image {
    display: block;
    max-width: 100%;
    max-height: 100%;
    margin: 0 auto;
  }
}

.hidden {
  display: none;
}
</style>
