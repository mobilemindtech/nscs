<script setup>


import { 
  ref, 
  computed, 
  onMounted, 
  onUnmounted 
} from 'vue';

const counter = ref(0);
const message = computed(() => {
  return `Blank {N}-Vue app: ${counter.value}`;
});

function logMessage() {
  console.log('You have tapped the message!');
}

let interval;
onMounted(() => {
  console.log('mounted');
  interval = setInterval(() => counter.value++, 100);
});

onUnmounted(() => {
  console.log('unmounted');
  clearInterval(interval);
});
</script>

<template web>

  <h1>NS ROCKS!</h1>

  <span @tap="logMessage">{{message}}</span>

</template>


<style>
/* .info {
    font-size: 20;
  } */
</style>

