import React from 'react';
import styled from 'styled-components';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import carouselImg1 from '../images/carousel1.jpg';

export default function SimpleSlider() {
  const settings = {
    dots: true, // 개수 표시 점
    infinite: true, // 무한 캐러셀
    speed: 500, // 다음 컨텐츠 까지의 속도
    slidesToShow: 1, // 화면에 보이는 컨텐츠 수
    slidesToScroll: 1, // 스크롤 시 넘어가는 컨텐츠 수
    autoplay: true, // 자동 캐러셀
    autoplaySpeed: 2000, // 자동 캐러셀 속도
    draggable: true, // 드래그
    fade: false, // 사라졌다 나타나는 효과
    arrows: true, // 좌,우 버튼
    vertical: false, // 세로 캐러셀
    initialSlide: 1, // 첫 컨텐츠 번호
    pauseOnFocus: true, // focus시 정지
    pauseOnHover: true, // hover시 정지
  };
  return (
    <Container>
      <Slider {...settings} className="slider-container">
        <div className="first-slider">
          <div className="first-slider-txt">
            <p>모두를 위한</p>
            <p>카쉐어링,</p>
            <h1>YATA</h1>
          </div>
        </div>
        <div>
          <h3>2</h3>
        </div>
        <div>
          <h3>3</h3>
        </div>
        <div>
          <h3>4</h3>
        </div>
        <div>
          <h3>5</h3>
        </div>
        <div>
          <h3>6</h3>
        </div>
      </Slider>
    </Container>
  );
}

const Container = styled.div`
  width: 100%;
  height: 100vh;
  margin: 0;
  padding: 0;

  .slider-container {
    padding: 1rem;
    height: 80%;
    display: flex;
    align-items: center;
  }

  .first-slider {
    width: 100%;
    height: 75vh;
    background-image: url(${carouselImg1});
    background-size: cover;
    color: white;

    .first-slider-txt {
      margin: 2rem;
    }

    p {
      font-size: 1.5rem;
      padding: 0.2rem;
    }

    h1 {
      font-size: 3rem;
      padding: 0.2rem;
    }
  }
`;
