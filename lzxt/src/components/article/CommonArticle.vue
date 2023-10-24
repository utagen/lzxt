<template>
  <section class="common-article">
    <header>
      <h2 class="title">{{title}}</h2>
      <div class="mark">
        <span>时间：{{createTime}}</span>
        <span v-if="author">编辑:&nbsp;xw{{author}}</span>
      </div>
    </header>
    <article class="content">
      <div v-if="image"><img :src="image" /></div>
      <div v-html="content"></div>
    </article>
  </section>
</template>

<script>
export default {
  name: 'common-article',
  props: {
    title: {
      type: String,
      required: true
    },
    author: String,
    createTime: {
      type: String,
      required: true
    },
    image: String,
    content: {
      type: String,
      required: true
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';
@import '~@/styles/mixins.scss';

.common-article {
  padding: 40px 40px;

  .title {
    @include ellipsis();

    font-size: $--font-size-extra-large * 1.5;
    line-height: 50px;
    text-align: center;
  }

  .mark {
    @include ellipsis();

    color: $--color-text-secondary;
    text-align: right;
    line-height: 25px;

    span {
      margin-left: 20px;

      &:first-child {
        margin-left: none;
      }
    }
  }

  .content {
    margin-top: 30px;
    min-height: 300px;

    img {
      display: block;
      max-width: 100%;
      margin: 0 auto;
    }
  }
}
</style>
