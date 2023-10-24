<template>
  <div class="empty-list">
    <div class="icon-wrap"><i class="lzxt-icon lzxt-icon-empty" /></div>
    <div class="content">
      <slot>空空如也！</slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'empty-list'
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.empty-list {
  background-color: $--color-white;
  padding: 100px 20px;
}

.icon-wrap {
  text-align: center;

  .lzxt-icon {
    color: $--color-text-secondary;
    font-size: 150px;
  }
}

.content {
  color: $--color-text-secondary;
  padding: 30px 0;
  font-size: $--font-size-medium;
  text-align: center;
}
</style>

<style lang="scss" scoped>
  @import '~@/styles/vars.scss';

  @media screen and (max-width: 480px) {
    .icon-wrap .lzxt-icon{
      font-size: 80px;
    }

    .content  {
      font-size: $--font-size-base ;
    }
  }
</style>
