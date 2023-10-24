<template>
  <div id="app" class="page">
    <common-header layout="layout-2"><h1 slot="nav" class="title">在线学习系统</h1></common-header>
    <div class="banner">
      <img src="@/images/banner/3.png" />
    </div>
    <section class="container">
      <aside class="aside">
        <nav-menu />
      </aside>
      <main class="main">
        <router-view />
      </main>
    </section>
    <common-footer/>
  </div>
</template>

<script>
import NavMenu from './NavMenu.vue'

import store from '@/store'

export default {
  name: 'exercise-layout',
  components: { NavMenu },
  async beforeRouteEnter (to, from, next) {
    await store.dispatch('field/loadBasicField')
    next()
  },
  data () {
    return { showUserPoster: true }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.page {
  background-color: $--exercise-background-color;
}

.title { text-align: center;}

.banner {
  position: relative;
  background: #9433f3;
  text-align: right;
  height: 120px;
  overflow: hidden;

  img {
    position: absolute;
    vertical-align: top;
    top: 0;
    right: 0
  }
}

.container {
  position: relative;

  .aside {
    background-color: $--color-white;
    position: absolute;
    width: 240px;
    left: 0;
    top: 0;
    height: 100%;
  }

  .main {
    margin-left: 260px;
    padding: 0;
    min-height: 450px;
    height: 100%;
  }
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 1200px) {
  .container {
    .aside {position: static; width: auto;}
    .main { margin: 0 20px;}
  }
}
@media screen and (max-width: 768px){
  .title {margin: 0; font-size: 20px; line-height: 74px; display: block;}
}

@media screen and (max-width: 499px){
  .title {height: 50px; line-height: 50px;}
}

@media screen and (max-width: 375px) {
  .container .main {
    margin: 0;
  }
}
</style>
