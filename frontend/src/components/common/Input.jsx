import styled from 'styled-components';

const Input = props => {
  const { label, state, setState, type, placeholder, maxLength, min, max } = props;

  const handleChange = e => {
    setState?.(e.target.value);
  };
  return (
    <StyledDiv>
      <StyledLabel htmlFor={label}>{label}</StyledLabel>
      <StyledInput
        id={label}
        type={type}
        min={min}
        max={max}
        value={state}
        onChange={handleChange}
        placeholder={placeholder}
        maxLength={maxLength}
      />
    </StyledDiv>
  );
};
const StyledDiv = styled.div`
  width: 100%;
  margin-top: 10px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  @media screen and (max-width: 470px) {
    flex-direction: column;
    align-items: normal;
  }
`;
const StyledLabel = styled.label`
  white-space: nowrap;
  font-size: ${props => props.theme.fontSizes.xxl};
  font-weight: bold;
  margin: 0 1.5rem 0 0;
  @media screen and (max-width: 470px) {
    margin-bottom: 7px;
  }
`;
const StyledInput = styled.input`
  width: 100%;
  padding: 10px 15px;
  border-radius: 5px;
  border: ${props => props.theme.colors.dark_blue + ' 1px solid'};
  :focus {
    outline: none;
    border-color: ${props => props.theme.colors.dark_blue};
  }
  @media screen and (min-width: 470px) {
    width: 70%;
  }
`;

export default Input;
