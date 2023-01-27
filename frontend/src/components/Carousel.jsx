import React from 'react';
import styled from 'styled-components';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import carouselImg1 from '../images/carousel1.jpg';
import carouselImg2 from '../images/carousel2.jpg';
import carouselImg3 from '../images/carousel3.jpg';
import carouselImg4 from '../images/carousel4.jpeg';

export default function SimpleSlider() {
  const settings = {
    dots: true, // 개수 표시 점
    infinite: true, // 무한 캐러셀
    speed: 500, // 다음 컨텐츠 까지의 속도
    slidesToShow: 1, // 화면에 보이는 컨텐츠 수
    slidesToScroll: 1, // 스크롤 시 넘어가는 컨텐츠 수
    autoplay: true, // 자동 캐러셀
    autoplaySpeed: 3000, // 자동 캐러셀 속도
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
        <div className="first-slider slider">
          <div className="text">
            <p>모두를 위한</p>
            <p>카쉐어링,</p>
            <h1>YATA</h1>
          </div>
        </div>
        <div className="second-slider slider">
          <div className="second-slider-txt text">
            <p>
              탑승자는 <strong>합리적인</strong> 가격으로,
            </p>
            <p>
              운전자는 안전하게 <strong>부수입을</strong>
            </p>
          </div>
        </div>
        <div className="third-slider slider">
          <div className="third-slider-txt text">
            <p>
              전국 <strong>어디서든</strong>
            </p>
            <p>
              <strong>아무때나</strong> 떠날 수 있는
            </p>
          </div>
        </div>
        <div className="fourth-slider slider">
          <div className="fourth-slider-txt text">
            <p>여정을 함께해요,</p>
            <h1>YATA</h1>
          </div>
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
    height: 85%;
  }

  .slider {
    width: 100%;
    height: 90vh;
    background-size: cover;
    color: white;

    .text {
      margin: 2rem;
    }

    h1 {
      font-size: 3rem;
      padding: 0.2rem;
    }

    p {
      font-size: 1.5rem;
      padding: 0.2rem;
    }

    strong {
      font-size: 2rem;
    }
  }

  .first-slider {
    background-image: url(${carouselImg1});
  }

  .second-slider {
    background-image: url(${carouselImg2});

    .second-slider-txt {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
    }
  }

  .third-slider {
    background-image: url(${carouselImg3});
    background-position: center;

    .third-slider-txt {
      height: 110%;
      display: flex;
      flex-direction: column;
      align-items: flex-start;
      justify-content: center;
    }
  }

  .fourth-slider {
    background-image: url(${carouselImg4});
    background-position: center;

    .fourth-slider-txt {
      height: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
    }
  }
`;
