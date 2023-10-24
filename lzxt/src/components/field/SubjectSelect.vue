<template>
  <el-select v-bind="$attrs" v-on="$listeners">
    <el-option v-for="(item, index) in subjects" :label="item" :key="index" :value="item"/>
  </el-select>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'subject-select',
  computed: {
    ...mapGetters('field', {
      subjects: 'conditionSubjects'
    })
  },
  created () {
    this.loadBasicField()
  },
  methods: {
    ...mapActions('field', ['loadBasicField'])
  }
}
</script>
