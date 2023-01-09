import { createGlobalStyle } from 'styled-components';
import AppleNormal from '../fonts/AppleSDGothicNeoL.ttf';
import AppleBold from '../fonts/AppleSDGothicNeoB.ttf';

const GlobalStyle = createGlobalStyle`
  *, *::before, *::after {
    box-sizing: border-box;

    margin: 0;
    padding: 0;
    border: 0;
  }
    @font-face {
        font-family: 'Apple SD';
        font-weight: 500;
        font-display: block;
        src: local('AppleNormal'), local('AppleNormal');
        font-style: normal;
        src: url(${AppleNormal}) format('truetype');
  }
  @font-face {
        font-family: 'Apple SD';
        font-weight: 600;
        font-display: block;
        src: local('AppleBold'), local('AppleBold');
        font-style: bold;
        src: url(${AppleBold}) format('truetype');
  }

 html, body, div, span, applet, object, iframe,
  h1, h2, h3, h4, h5, h6, p, blockquote, pre,
  a, abbr, acronym, address, big, cite, code,
  del, dfn, em, img, ins, kbd, q, s, samp,
  small, strike, strong, sub, sup, tt, var
  b, u, i, center,
  dl, dt, dd, ol, ul, li,
  fieldset, form, label, legend,
  table, caption, tbody, tfoot, thead, tr, th, td,
  article, aside, canvas, details, embed, 
  figure, figcaption, footer, header, hgroup, 
  menu, nav, output, ruby, section, summary,
  time, mark, audio, video {
    margin: 0;
    padding: 0;
    border: 0;
    /* font-size: calc( 10px + 0.4vw ); */
    /* 임시용 470px 기준 font size */
    font-size: 12px;
    vertical-align: baseline;
    font-family: 'Apple SD';
    /* background-color: #FBFBFB; */
    /* overflow-y: auto;  */
    /* overflow: overlay; */
    /* overflow-x: hidden; */
  }
  a {
    text-decoration: none;
    color: inherit;
  }
  /* HTML5 display-role reset for older browsers */
  article, aside, details, figcaption, figure, 
  footer, header, hgroup, menu, nav, section {
    display: block;
  }
  body {
    line-height: 1;
    background-color: #FBFBFB;
    overflow-y: scroll !important;
  }
  ol, ul {
    list-style: none;
  }
  blockquote, q {
    quotes: none;
  }
  blockquote:before, blockquote:after,
  q:before, q:after {
    content: '';
    content: none;
  }
  table {
    border-collapse: collapse;
    border-spacing: 0;
  }
  body::-webkit-scrollbar {
    width: 8px;
    background: transparent;
}
body::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.3);
    border-radius: 100px;
}
`;

export default GlobalStyle;
