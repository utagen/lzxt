<template>
  <div class="course-list-item">
    <div class="course-name">{{item.courseName}}</div>
    <div class="cell cell-1">
      <label>状态：</label><span :class="statusClasses">{{statusText}}</span></div>
    <div class="cell cell-2">
      <div><label>购买时间：</label><span>{{item.buyTime}}</span></div>
      <div> <label>到期时间：</label><span>{{item.expireTime}}</span></div>
    </div>
      <div class="cell cell-3">
      <label>学习次数：</label><span>{{ item.studyCount }}</span>
    </div>
    <div class="cell cell-4">
      <el-button :type="valid ? 'primary': 'info'" @click="gotoLearn" :disabled="!valid">开始学习</el-button>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'my-course-list-item',
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  computed: {
    valid () {
      return this.item.status === 1
    },
    statusText () {
      return this.valid ? '有效' : '失效，已到期'
    },
    statusClasses () {
      return ['status', { valid: this.valid, invalid: !this.valid }]
    }
  },
  methods: {
    ...mapActions('course', ['isInPractice']),
    gotoLearn () {
      const { courseId, status } = this.item
      const params = this.$encode({ id: courseId, status })
      this.$router.push(`/courses/${params}/choose/unit`)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';
@import '~@/styles/mixins.scss';

.course-list-item {
  position: relative;
  background-color: $--color-white;
  border-radius: $--border-radius-base;
  border: $--border-base;
  margin-bottom: 20px;
  padding: 40px 20px 20px;

  &:hover {
    box-shadow: $--box-shadow-base;
  }

  .course-name {
    @include ellipsis();

    position: absolute;
    padding: 0 10px;
    text-align: center;
    width: 220px;
    color: $--color-white;
    background-color: $--color-primary-light-1;
    height: 40px;
    line-height: 40px;
    border-bottom-left-radius: $--border-radius-base;
    border-bottom-right-radius: $--border-radius-base;
    top: 0;
    left: 20px;
  }

  .cell {
    display: inline-block;
    width: 28%;
    line-height: 30px;

    &:last-child {
      width: 16%;
    }

    .status {
      &.valid {
        color: $--color-success;
      }

      &.invalid {
        color: $--color-text-secondary;
      }
    }
  }
}
</style>

<style lang="scss" scoped>
@media screen and (max-width: 740px) {
  .course-list-item {
    .cell-1 { width: 20%;}
    .cell-2 { width: 44%;}
    .cell-3 { width: 20%;}
  }
}
@media screen and (max-width: 530px) and (min-width: 450px) {
  .course-list-item {
    .cell-1 { width: 25%;}

    .cell-2 { width: 50%;}
    .cell-3 {
      float: right;
      width: 25%;
    }
    .cell-4 {
      width: 25%!important;
      float: right;
      transform:translateY(-32px)
    }
  }
}

@media screen and (max-width: 450px) {

  .course-list-item {
    .cell {
      width: 100%;
    }

    .course-name {
      left: 50%;
      transform: translateX(-50%);
      max-width: 90%;
    }
  }
}
</style>
