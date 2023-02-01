import styled from 'styled-components';

const Input = props => {
  const { label, state, setState, type, placeholder, maxLength, onChange, onFocus, onBlur, min, max, readOnly, step } =
    props;

  const handleChange = e => {
    setState?.(e.target.value);
  };

  return (
    <StyledDiv>
      <StyledLabel htmlFor={label}>{label}</StyledLabel>
      {onChange ? (
        <StyledInput
          id={label}
          type={type}
          min={min}
          max={max}
          value={state}
          onChange={onChange}
          onFocus={onFocus}
          onBlur={onBlur}
          placeholder={placeholder}
          maxLength={maxLength}
          readOnly={readOnly}
          step={step}
        />
      ) : (
        <StyledInput
          id={label}
          type={type}
          min={min}
          max={max}
          value={state}
          onChange={handleChange}
          onFocus={onFocus}
          onBlur={onBlur}
          placeholder={placeholder}
          maxLength={maxLength}
          readOnly={readOnly}
          step={step}
        />
      )}
    </StyledDiv>
  );
};
const StyledDiv = styled.div`
  width: 100%;
  margin-top: 1.5rem;
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
  font-size: 1rem;

  @media screen and (max-width: 470px) {
    margin-bottom: 1rem;
  }
`;
const StyledInput = styled.input`
  width: 100%;
  padding: 0.8rem 1.25rem;
  border-radius: 5px;
  /* border: ${props => props.theme.colors.dark_blue + ' 1px solid'}; */
  border: 1px solid lightgray;
  :focus {
    outline: none;
    border-color: ${props => props.theme.colors.darker_blue};
  }
  /* background-color: ${props => props.readOnly && '#dddddd'}; */
  @media screen and (min-width: 470px) {
    width: 80%;
  }
`;

export default Input;
