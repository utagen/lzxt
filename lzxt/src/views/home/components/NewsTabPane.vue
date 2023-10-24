<template>
  <ul class="news-list" v-if="list.length">
    <li v-for="item in list" :key="item.id">
      <router-link :to="`/news/${item.id}`">{{item.title}}</router-link>
    </li>
  </ul>
</template>

<script>
export default {
  name: 'news-pane',
  props: {
    list: {
      type: Array,
      default: () => ([])
    }
  }
}
</script>
<style lang="scss" scoped>
@import "~@/styles/vars.scss";
@import '~@/styles/mixins.scss';

.news-list {
  line-height: 36px;

  li {
    @include ellipsis();
  }
}
</style>
