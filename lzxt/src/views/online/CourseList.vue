<template>
  <section>
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>在线课程</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="exercise-tab">
      <el-radio-group v-model="grade" @change="hanleGradeChange" class="grade-list">
        <el-radio-button v-for="(item, index) in grades" :label="item" :key="index"/>
      </el-radio-group>
    </div>
    <empty-list v-if="isEmpty"></empty-list>
    <template v-else>
      <div class="grid-wrap">
        <online-course-list-item v-for="item in courseList" :key="item.id" :item="item" />
      </div>
      <div class="footer-wrap" v-if="showFooter">
        <el-pagination
          layout="total, prev, pager, next, jumper"
          hide-on-single-page
          :current-page.sync="page"
          :page-size="pageSize"
          :total="total"
          @current-change="switchPagination">
        </el-pagination>
      </div>
    </template>
  </section>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import OnlineCourseListItem from './CourseListItem.vue'

export default {
  name: 'online-course-list',
  components: { OnlineCourseListItem },
  data () {
    return { grade: '七年级', page: 1, pageSize: 10 }
  },
  computed: {
    ...mapState('course', {
      courseList: state => state.onlineCourseList.list,
      total: state => state.onlineCourseList.total
    }),
    ...mapState('field', ['grades']),
    isEmpty () {
      return this.total === 0
    },
    showFooter () {
      return this.total > this.pageSize
    }
  },
  mounted () {
    this.getCourseList({ grade: this.grade, page: this.page })
  },
  methods: {
    ...mapActions('course', {
      getCourseList: 'getOnlineCourseList'
    }),
    hanleGradeChange (grade) {
      this.getCourseList({ grade, page: 1 })
    },
    switchPagination (page) {
      this.getCourseList({ grade: this.grade, page })
    }
  }
}
</script>

<style lang="scss">
@import '~@/styles/vars.scss';
.grade-list .el-radio-button {

  .el-radio-button__inner {
    font-size: $--font-size-medium;
    border-top: 0;
    border-bottom: 0;
    padding: 15px 30px;
    font-weight: 900;
  }

  &:first-child .el-radio-button__inner {
    border-bottom-left-radius: 0;
    border-left: 0;

  }

  &:last-child .el-radio-button__inner {
    border-bottom-right-radius: 0;
    border-right: 0;
  }

  .el-radio-button__orig-radio:checked + .el-radio-button__inner {
    background-color: $--color-white;
    color: $--color-primary;
    border-color: $--border-color-base;
    box-shadow: none;
  }
}
</style>

<style lang="scss" scoped>
@import '~@/styles/vars.scss';

.grid-wrap {
  padding-top: 1px;
  background-color: $--color-white;
}
</style>

<style lang="scss">
@media screen and (max-width: 619px) {
  .grade-list {
    display:flex;
    justify-content: space-between;
    text-align: center;

   .el-radio-button {
     flex: 1;

     .el-radio-button__inner {
       display: block;
       padding: 15px 0;
     }
   }
  }
}
</style>
