<template>
  <section class="container">
    <school-news class="container-item"></school-news>
    <case-anlysis></case-anlysis>
    <popular-cases></popular-cases>
  </section>
</template>

<script>
import SchoolNews from './components/SchoolNews.vue'
import CaseAnlysis from './components/CaseAnlysis.vue'
import PopularCases from './components/PopularCases'

export default {
  name: 'home',
  components: { SchoolNews, CaseAnlysis, PopularCases }
}
</script>

<style lang="scss" scoped>
.container-item {
  margin: 50px 0;
}
</style>

<style lang="scss" scoped>
  @media screen and (max-width: 1280px) {
    .container {margin: 20px}
  }
  @media screen and (max-width: 768px) {
    .container-item {margin: 20px 0}
  }
</style>
