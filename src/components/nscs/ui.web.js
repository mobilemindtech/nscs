

export const Label = {
  props: ['text', 'cls'],
  setup() {
    
  },
  template: "<span :class=\"cls\">{{text}}!!</span>",
}
export const Frame = {
  template: "<div><slot></slot></div>"
}
export const Page = {
  template: "<div><slot></slot></div>"
}
export const ActionBar = {
  template: "<div style='height: 50px; width: 100%; background-color: blue;'><slot></slot></div>"
}
export const GridLayout = {
  props: ['rows', 'cls'],
  template: "<div :class=\"cls\"><slot></slot></div>"
}
