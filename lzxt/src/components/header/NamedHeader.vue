<template>
  <header class='named-header'>
    <h2><slot><span>{{title}}</span></slot></h2>
  </header>
</template>

<script>
export default {
  name: 'named-header',
  props: {
    title: String
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.named-header{
  background-color: $--color-white;
  border-bottom: $--border-base;

  h2 {
    margin: 0;
    padding: 0 0 0 50px;
    font-weight: normal;
    font-size: $--font-size-large;
    line-height: 60px;
    border-left: 10px solid $--color-primary;
  }
}
</style>
