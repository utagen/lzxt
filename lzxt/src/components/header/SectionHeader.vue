<template>
  <h2 class="section-header">
    <router-link v-if="more" :to="more">更多</router-link>
    <span>{{title}}</span>
  </h2>
</template>

<script>
export default {
  name: 'section-header',
  props: {
    title: {
      type: String,
      required: true
    },
    more: [String, Object]
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/vars.scss";
.section-header {
  margin: 0;
  font-size: $--font-size-medium;
  line-height: 40px;
  font-weight: normal;
  border-bottom: 1px solid $--color-primary;

  span {
    display: inline-block;
    text-align: center;
    padding: 0 25px;
    background-color: $--color-primary;
    color: $--color-white;
  }

  a {
    font-size: $--font-size-base;
    float: right;
  }
}
</style>
