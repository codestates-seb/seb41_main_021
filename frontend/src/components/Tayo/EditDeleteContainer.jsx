import styled from 'styled-components';
import { FiEdit, FiTrash } from 'react-icons/fi';
import { useNavigate } from 'react-router';

export default function EditDeleteContainer(props) {
  const { state, yataId } = props;
  const navigate = useNavigate();

  return (
    <CRUDContainer>
      <FiEdit onClick={() => navigate(`/${state}-edit/${yataId}`)} />
      <FiTrash />
    </CRUDContainer>
  );
}

const CRUDContainer = styled.div`
  width: 95%;
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: flex-end;

  svg {
    font-size: 1.7rem;
    margin: 0.5rem 0.8rem;
    cursor: pointer;
  }
`;
