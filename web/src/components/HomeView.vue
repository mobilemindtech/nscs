<script setup>


import { 
  ref, 
  computed, 
  onMounted, 
  onUnmounted,
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


const MyLabel = {
  props: ['text', 'cls'],
  setup() {
    console.log(`MyLabel setup`)
  },
  template: "<span :class=\"cls\">{{text}}!!</span>",
}
const MyFrame = {
  template: "<div><slot></slot></div>"
}
const MyPage = {
  template: "<div><slot></slot></div>"
}
const MyActionBar = {
  template: "<div style='height: 50px; width: 100%; background-color: blue;'><slot></slot></div>"
}
const MyGridLayout = {
  props: ['rows', 'cls'],
  template: "<div :class=\"cls\"><slot></slot></div>"
}




</script>


<template>
  <MyFrame>
    <MyPage>
      <MyActionBar>
        <MyLabel text="Home" class="font-bold text-lg" cls="font-bold text-lg"/>
      </MyActionBar>

      <MyGridLayout rows="auto, *" class="px-4" cls="px-4">
        <MyLabel text="generic label!"/>
        <MyLabel
          row="1"
          class="text-xl align-middle text-center text-gray-500"
          :text="message"
          @tap="logMessage"
        />

      </MyGridLayout>
    </MyPage>
  </MyFrame>
</template>

<style>
/* .info {
    font-size: 20;
  } */
</style>

