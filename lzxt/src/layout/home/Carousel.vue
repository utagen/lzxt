<template>
  <div class="el-carousel el-carousel--horizontal" @mouseenter.stop="handleMouseEnter" @mouseleave.stop="handleMouseLeave">
    <div class="el-carousel__container">
      <transition name="carousel-arrow-left">
        <button v-show="hover" class="el-carousel__arrow el-carousel__arrow--left" @click.stop="switchCarousel(activeIndex - 1)"><i class="el-icon-arrow-left"/></button>
      </transition>
      <transition  name="carousel-arrow-right">
        <button v-show="hover" class="el-carousel__arrow el-carousel__arrow--right" @click.stop="switchCarousel(activeIndex + 1)"><i class="el-icon-arrow-right"/></button>
      </transition>
      <div ref="items" v-for="(item,index) in carouselItems" :class="`el-carousel-item el-carousel-item-${index}`" :key="index">
        <a href="#" @click.prevent><img :src="item" alt="carousel"/></a>
      </div>
    </div>
    <ul class="el-carousel__indicators el-carousel__indicators--horizontal">
      <li
        v-for="(item, index) in items"
        :key="index"
        :class="['el-carousel__indicator', 'el-carousel__indicator--horizontal', { 'is-active': index === activeIndex }]"
        @click.stop="switchCarousel(index)">
        <button class="el-carousel__button"></button>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'carousel',
  props: {
    images: {
      type: Array,
      required: true
    },
    interval: {
      type: Number,
      default: 5000
    }
  },
  computed: {
    carouselItems () {
      return this.images.slice(0, 5)
    }
  },
  data () {
    return {
      timer: 0,
      activeIndex: 0,
      items: [],
      size: 0,
      hover: false

    }
  },
  mounted () {
    this.items = this.$refs.items
    this.size = this.items.length
    this.switchCarousel(this.activeIndex)
  },
  methods: {
    handleMouseEnter () {
      this.hover = true
    },
    handleMouseLeave () {
      this.hover = false
    },
    switchCarousel (index) {
      const next = (index + this.size) % this.size
      if (this.timer) {
        clearTimeout(this.timer)
      }
      this.items[this.activeIndex].style.cssText = 'opacity: 0; z-index: 1'
      this.items[next].style.cssText = 'opacity: 1; z-index: 0'
      this.activeIndex = next
      this.timer = setTimeout(() => {
        this.switchCarousel(this.activeIndex + 1)
      }, this.interval)
    }
  },
  beforeDestroy () {
    clearTimeout(this.timer)
  }
}
</script>

<style lang="scss" scoped>
  .el-carousel {
    &__container {
      height: auto;
      overflow: hidden;
    }

    &-item {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    transition: opacity 1s ease-in-out;
    opacity: 0;

    &-0 {
      position: relative;
      opacity: 1;
    }

    img { width: 100%; vertical-align: top;}
    }
  }
</style>
