<template>
  <section class="course-list-wrap">
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>我的课程</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="grid-wrap">
      <my-course-list-item v-for="item in courseList" :item="item" :key="item.id" />
    </div>
  </section>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import MyCourseListItem from './components/CourseListItem.vue'

export default {
  name: 'my-course-list',
  components: { MyCourseListItem },
  computed: {
    ...mapState('course', { courseList: 'purchasedCourseList' })
  },
  mounted () {
    this.getCourseList()
  },
  methods: {
    ...mapActions('course', { getCourseList: 'getPurchasedCourseList' })
  }
}
</script>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.grid-wrap {
  background-color: transparent;
}

.course-list-wrap {
  padding-top: 0px;
  height: 100%;
  background-color: $--exercise-background-color;
}

</style>
