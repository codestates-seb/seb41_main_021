import styled from 'styled-components';
import { IoAlertCircle } from 'react-icons/io5';
import Bubble from './Bubble';

const Validity = ({ type }) => {
  return (
    <ValiditySection>
      <IoAlertCircle className="icon" />
      <Bubble type={type} />
    </ValiditySection>
  );
};

const ValiditySection = styled.div`
  height: 1px;
  width: 1px;
  position: absolute;
  right: -1rem;
  top: 1rem;
  display: flex;
  flex-direction: column;
  /* display: ${props => (props.error ? 'none' : 'flex')};
  flex-direction: ${props => props.error && 'column'}; */
  .bubble {
    visibility: hidden;
  }
  :hover {
    .bubble {
      visibility: visible;
    }
  }
  .icon {
    font-size: 25px;
    color: ${props => props.theme.colors.darker_blue};
    position: absolute;
    cursor: pointer;
  }
  @media screen and (max-width: 800px) {
    top: 3.5rem;
  }
`;

export default Validity;
