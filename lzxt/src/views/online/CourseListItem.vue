<template>
  <div class="course-list-item" :class="{purchased: isPurchased}">
    <div class="purchased-icon" v-if="isPurchased"><i class="lzxt-icon lzxt-icon-purchased" /></div>
    <div class="course-list-item_bg" v-if="backgroundImage">
      <img :src="backgroundImage" />
    </div>
    <div class="course-list-item__header">
      <div class="course-purchased-count">&nbsp;<!--span>已有{{item.studyCount}}人加入学习</span--></div>
      <h3 class="course-name">{{item.courseName}}</h3>
    </div>
    <div v-if="isPurchased" class="course-list-item__body purchased">
      <div><label>学科：</label><span>{{item.subjectInfo.subjectName}}</span></div>
      <div><label>年级：</label><span>{{item.subjectInfo.subjectGrade}}</span></div>
      <div><label>学期：</label><span>{{item.subjectInfo.subjectTerm}}</span></div>
      <div><span>有效期至{{item.expireTime}}</span></div>
      <div class="button-wrap">
        <el-button type="primary" @click.stop="toLearnCourse"><span>立即学习</span></el-button>
      </div>
    </div>
    <div v-else class="course-list-item__body unpurchased" :style="backgroundImage">
      <div><label>有效期：</label><span>{{item.orderTime}}天</span></div>
      <div>自购买之日起{{item.orderTime}}日内，该课程可使用。</div>
      <div><label>原价：</label><span>{{item.coursePrice}}元</span></div>
      <div><label>售价：</label><span>{{item.courseZhePrice}}元</span></div>
      <div class="button-wrap">
        <el-button @click.stop="toLearnCourse"><span>试用</span></el-button>
        <el-button type="primary" @click.stop="handlePurch"><span>购买</span></el-button>
      </div>
    </div>

  </div>
</template>

<script>

export default {
  name: 'online-course-list-item',
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  computed: {
    isPurchased () {
      return this.item.buy === 1
    },
    backgroundImage () {
      return this.item.imageUrl
    }
  },
  methods: {
    toLearnCourse () {
      const params = this.$encode({ id: this.item.id, status: this.item.buy })
      this.$router.push(`/courses/${params}/choose/unit`)
    },
    handlePurch () {
      // const params = encodeParamsToUrl({ courseId: this.item.id })
      const params = this.$encode({ courseId: this.item.id })
      this.$router.push(`/create/${params}/order`)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';
@import '~@/styles/mixins.scss';

$--course-body-space: 10px;

.course-list-item {
  position: relative;
  vertical-align: top;
  display: inline-block;
  border-spacing: 0;
  width: 30%;
  margin: 20px 1.25% 0;
  height: 300px;

  border: $--border-base;
  border-radius: $--border-radius-base;
  background: linear-gradient(
    to bottom,
    $--color-primary-light-8 0px,
    $--color-primary-light-8 100px,
    $--background-color-base 100px,
    $--background-color-base 100%
  );

  &.purchased {
    .button-wrap {
      margin-top: 5px;
      display: none;
      text-align: center;
    }
  }

  &:hover {
    box-shadow: $--box-shadow-base;

    &.purchased {
      .button-wrap {
        display: block;
      }
    }
  }

  &:nth-child(3n + 1) {margin-left: 2.5%;}

  .purchased-icon {
    position: absolute;
    top: 70px;
    left: 50%;
    margin-left: -30px;
    width: 60px;
    height: 60px;
    border-radius: 100%;
    z-index: 80;

    .lzxt-icon {
      font-size: 60px;
      color: $--color-danger;
      opacity: .6;
    }
  }

  .course-list-item_bg {
    position: absolute;
    z-index: 40;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    text-align: center;

    img {
      width: 100%;
      height: 100%;
      vertical-align: middle;
      object-fit: cover;
    }
  }
}

.course-list-item__header {
  padding: $--course-body-space;
  background-repeat: no-repeat;
  background-size: cover;
  position: relative;
  z-index: 100;

  .course-purchased-count {
    line-height: 20px;
    color: $--color-text-regular;
    font-size: $--font-size-extra-small;
  }

  .course-name {
    @include ellipsis();

    margin: 0;
    text-align: center;
    font-size: $--font-size-medium;
    line-height: 60px;
  }
}

.course-list-item__body {
  position: relative;
  z-index: 100;
  height: 198px;
  padding: 20px 40px;
  border-bottom-left-radius: $--border-radius-base;
  border-bottom-right-radius: $--border-radius-base;
  background-repeat: no-repeat;
  background-size: cover;
  background-position-y: -100px;

  &.purchased  > div{
    @include ellipsis();
    line-height: 30px;
  }

  &.unpurchased > div {

    text-align: center;

    &:first-child,
    &:nth-child(3),
    &:nth-child(4) {
      font-size: $--font-size-medium;
      @include ellipsis();
      line-height: 30px;
    }

    &:nth-child(3) {
      color: $--color-text-secondary;
      text-decoration: line-through;
    }

    &:nth-child(3),
    &:nth-child(4)  {
      font-size: $--font-size-base;
    }

    &:nth-child(2) {
      padding: 6px 0;
      font-size: $--font-size-extra-small;
    }

    &:last-child {
      margin-top: 20px;

      .el-button {
        &:first-child {
          margin-right: 30px;
        }
        &:last-child {
          margin-right: 0;
        }
      }

      span {
        display: inline-block;
        width: 40px;
      }
    }
  }
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 930px ) and (min-width: 620px) {
  .course-list-item {
    width: 46.25%;
    margin: 20px 1.25% 0;
  }
}

@media screen and (max-width: 619px) {
  .course-list-item {
    display: block;
    width: auto;
    margin: 20px 20px 0!important;
  }
}

@media screen and (max-width: 360px) {
  .course-list-item {
    display: block;
    width: auto;
    margin: 20px 20px 0!important;
  }

  .course-list-item__body {
    padding: 20px;
  }
}
</style>
