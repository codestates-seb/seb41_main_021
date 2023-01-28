import styled from 'styled-components';
import { FiEdit, FiTrash } from 'react-icons/fi';
import { useNavigate } from 'react-router';
import useDeleteData from '../../hooks/useDeleteData';

export default function EditDeleteContainer(props) {
  const { state, yataId } = props;
  const navigate = useNavigate();

  const deleteHandler = () => {
    useDeleteData(`https://server.yata.kro.kr/api/v1/yata/${yataId}`).then(() => {
      navigate(`/${state}-list`);
    });
  };

  return (
    <CRUDContainer>
      <FiEdit onClick={() => navigate(`/${state}-edit/${yataId}`)} title="수정하기" />
      <FiTrash onClick={deleteHandler} title="삭제하기" />
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
