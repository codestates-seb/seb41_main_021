import { Link } from 'react-router-dom';
import styled from 'styled-components';

const LinkTo = props => {
  const { message, link, linkText } = props;
  return (
    <StyledLinkToSign>
      {message}{' '}
      <Link to={link}>
        <StyledSpan>{linkText}</StyledSpan>
      </Link>
    </StyledLinkToSign>
  );
};

const StyledLinkToSign = styled.div`
  margin: 1.5rem;
  text-align: center;
`;

const StyledSpan = styled.span`
  color: ${props => props.theme.colors.dark_blue};
  font-weight: bold;
`;
export default LinkTo;
