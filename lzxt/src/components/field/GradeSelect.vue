<template>
  <el-select v-bind="$attrs" v-on="$listeners">
    <el-option v-for="(item, index) in gradesList" :label="item" :value="item" :key="index" />
  </el-select>
</template>

<script>
import { mapState, mapGetters } from 'vuex'

export default {
  name: 'grade-select',
  props: {
    enableAll: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    ...mapState('field', ['grades']),
    ...mapGetters('field', ['conditionGrades']),

    gradesList () {
      const { enableAll, grades, conditionGrades } = this
      return enableAll ? conditionGrades : grades
    }
  }
}
</script>
