
# NativeScript Code Share (Vue) 

Attempted code sharing between a NativeScriptVue 3 app and a Vue3 web app.


ATTENTION: working in progress.

# Run

```shell

# run web app
> ./nsx web 

# run ns app
> ./nsx native <android | ios>

# run web and ns app
> ./nsx all <android | ios>

``` 

## Structure

./src -> Shared code
./native -> NativeScriptVue project
./web -> Vue project
./npx -> Runner

### Sintax

Into js file we can use special sintax to sepere Web and Native (mobile) code. 
But I'm still thinking about how this can mess up the ide and cause duplicate declaration errors, but using sublimetext for quick prototyping works well.

```js
//=native
import { createApp } from 'nativescript-vue';
//=

//=web
import { createApp } from 'vue/dist/vue.esm-bundler'
//=

import HomeView from './components/HomeView.vue';

let app = createApp(HomeView);

//=native
app.start();
//=

//=web
app.mount('#app')
//=

```

### File by platform

We can also define files for platform, using:


* `main.native.js` only native
* `main.web.js` only web


In this case, the file will be renamed without the platform indicator, in this case `main.js`.


### Templates

Templates can be identified according to the desired platform

```html
<template native>
  <Frame>
    <Page>
      <ActionBar>
        <Label text="Home" class="font-bold text-lg"/>
      </ActionBar>
        <Label text="my label"/>
    </Page>
  </Frame>
</template>

<template web>
  <div class='frame'>
    <div class='page'>
      <div class='action-bar'>
        <span class="font-bold text-lg">Home</span>
      </div>
        <span text="my label"/>
    </div>
  </div>
</template>

``` 

Or you can build the appropriate components to use the same templete


```js
<script setup>

//=native
import {
  ref,
  computed,
  onMounted,
  onUnmounted,  
} from 'nativescript-vue';
import {Label, Frame, Page, ActionBar, GridLayout} from '@nativescript/core'
//=

//=web
import { 
  ref, 
  computed, 
  onMounted, 
  onUnmounted,
} from 'vue';
//=

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


//=web
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
//=


//=native
const MyLabel = Label
const MyFrame = Frame
const MyPage = Page
const MyActionBar = ActionBar
const MyGridLayout = GridLayout
//=


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

```