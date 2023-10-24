<template>
  <section class="form-container">
    <div class="breadcrumb-wrap exercise">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item to="/coruses">我的课程</el-breadcrumb-item>
        <el-breadcrumb-item>选择单元</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <named-header title="选择单元"></named-header>
    <div class="form-wrap">
      <el-form ref="form" label-position="left" label-width="120px" :model="form" :rules="rules" @submit.native.prevent="goToLearn">
        <el-form-item label="学科名称:" prop="subjectId">
          <el-select v-model="form.subjectId" @change="handleSubjectChange">
            <el-option v-for="item in courseSubjects" :label="item.subjectName" :value="item.id" :key="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item v-if="showUnits" label="单元:" prop="checkedUnits">
          <el-checkbox :disabled="inprogress" :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
          <el-checkbox-group :disabled="inprogress" v-model="form.checkedUnits" @change="handleCheckedUnitChange">
            <el-checkbox v-for="unit in unitList" :label="unit" :key="unit">{{unit}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label-width="0">
          <div class="button-group">
            <el-button @click="$router.push('/courses')">取消</el-button>
            <el-button type="primary" native-type="submit">开始学习</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </section>
</template>

<script>
import { mapActions, mapState } from 'vuex'

export default {
  name: 'choose-course-unit',
  props: {
    course: {
      type: String,
      required: true
    },
    trial: Boolean
  },
  data () {
    const { id, status } = this.$decode(this.course)

    return {
      courseId: id,
      isIndeterminate: false,
      checkAll: false,
      form: {
        checkedUnits: [],
        subjectId: '',
        status: status
      },
      rules: {
        checkedUnits: [
          { required: true, message: '请选择单元！' }
        ]
      }
    }
  },
  computed: {
    ...mapState('course', ['courseSubjects']),
    showUnits () {
      return this.form.status === 1
    },
    choosedSubject () {
      const choosed = this.courseSubjects.filter(item => item.id === this.form.subjectId)
      return choosed.shift()
    },
    unitList () {
      return this.choosedSubject ? this.choosedSubject.subjectUnitList : []
      // let units = []
      // if (this.showUnits && this.form.subjectId) {
      //   for (var i = 0, len = this.courseSubjects.length; i < len; i++) {
      //     if (this.form.subjectId === this.courseSubjects[i].id) {
      //       units = this.courseSubjects[i].subjectUnitList
      //       break
      //     }
      //   }
      // }
      // return units
    },
    inprogress () {
      return !!(this.choosedSubject && this.choosedSubject.subjectUnitSelectedList && this.choosedSubject.subjectUnitSelectedList.length > 0)
    }
  },
  async mounted () {
    const subjects = await this.getCoursSubjects(this.courseId)
    if (subjects && subjects.length) {
      this.$set(this.form, 'subjectId', subjects[0].id)
      this.handleSubjectChange()
      // this.$set(this.form, 'checkedUnits', subjects[0].choosedSubject)
    }
  },
  methods: {
    ...mapActions('course', ['getCoursSubjects']),
    async goToLearn () {
      try {
        await this.$refs.form.validate()
        const { subjectId, status, checkedUnits } = this.form
        if (status === 0) {
          const name = this.courseSubjects.filter(item => item.id === subjectId).pop().subjectName
          const params = this.$encode({ id: subjectId, name })
          this.$router.push(`/trial/courses/${params}`)
        } else {
          const params = this.$encode({ id: subjectId, units: checkedUnits })
          this.$router.push(`/courses/${params}/learning`)
        }
      } catch (e) {}
    },
    handleCheckAllChange (checkAll) {
      const checkedUnits = checkAll ? [].slice.call(this.unitList) : []
      this.$set(this.form, 'checkedUnits', checkedUnits)
      this.isIndeterminate = false
    },
    handleCheckedUnitChange (checkedUnits) {
      const counts = checkedUnits.length
      const total = this.unitList.length
      this.checkAll = counts === total
      this.isIndeterminate = counts > 0 && counts < total
    },
    handleSubjectChange () {
      const { subjectUnitSelectedList, subjectUnitList } = this.choosedSubject
      this.$set(this.form, 'checkedUnits', subjectUnitSelectedList || [])
      this.checkAll = subjectUnitSelectedList && subjectUnitSelectedList.length === subjectUnitList.length
      this.$nextTick(() => {
        this.$refs.form.clearValidate()
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@media screen and (max-width: 550px) {
  .button-group {
    padding: 30px 0;
    .el-button {
      margin-left: 30px
    }

  }
}

@media screen and (max-width: 340px) {
  .button-group .el-button {
    margin-left: 20px;

    &:first-child {
      margin-left: 0;
    }
  }
}
</style>
