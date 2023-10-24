<template>
  <div id="app">
    <common-header layout="layout-1"><nav-menu slot="nav"/></common-header>
    <carousel :images="images" :interval="10000"></carousel>
    <router-view />
    <common-footer />
    <quick-service></quick-service>
  </div>
</template>

<script>
import NavMenu from './NavMenu.vue'
import QuickService from './QuickService.vue'
import Carousel from './Carousel.vue'

import img1 from '@/images/banner/1.jpg'
import img2 from '@/images/banner/2.jpg'

export default {
  name: 'common-layout',
  components: { NavMenu, QuickService, Carousel },
  computed: {
    carouselHeight () {
      const imgw = 1920
      const imgh = 500
      const docw = document.documentElement.clientWidth
      return `${Math.floor(Math.max((docw * imgh) / imgw, 300))}px`
    },
    images () {
      return [img1, img2]
    }
  }
}
</script>

<style lang="scss" scoped>
.el-carousel__item img {
  max-height: 100%;
}
</style>
