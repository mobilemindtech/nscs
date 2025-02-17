<script lang="js" setup>



import { 
  ref, 
  computed, 
  onMounted, 
  onUnmounted,
} from './nscs/vue';

import { 
  Label,
  Frame,
  Page,
  ActionBar,
  GridLayout,
} from './nscs/ui';


import {multiply} from '../scala/calcule'
import {ScalaComponent,ScalaComponent2, ScalaVComponent1} from '../scala/component'

import LoginView from '../scala/LoginView'

////
console.log("multiply = " + multiply(1,1));

const counter = ref(0);
const message = computed(() => {
  return `Blank {N}-Vue app: ${counter.value} =)`;
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


let SComponent1 = new ScalaComponent()
let SComponent = ScalaComponent2


</script>


<template>
  <Frame>
    <Page>
      <ActionBar>
        <Label text="Home" class="font-bold text-lg" cls="font-bold text-lg"/>
      </ActionBar>

      <GridLayout rows="auto, *" class="px-4" cls="px-4">
        <Label text="generic label!"/>
        <SComponent text="yeahh!!"/>
        <SComponent1 text="uwooooo" />
        <ScalaVComponent1 text="v component!" />
        <Label
          row="1"
          class="text-xl align-middle text-center text-gray-500"
          :text="message"
          @tap="logMessage"
        />

        <LoginView propText="prop value"/>

      </GridLayout>
    </Page>
  </Frame>
</template>

<style>
/* .info {
    font-size: 20;
  } */
</style>
