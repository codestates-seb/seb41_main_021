import './App.css';
import GlobalStyle from './styles/GlobalStyle';
import { Route, Routes } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import theme from './styles/theme';
import Screen from './components/Screen';
import ChattingDetail from './pages/ChattingDetail';
import ChattingList from './pages/ChattingList';
import Homepage from './pages/Homepage';
import JourneyList from './pages/JourneyList';
import Loading from './pages/Loading';
import LogIn from './pages/LogIn';
import Map from './pages/Map';
import MyPage from './pages/MyPage';
import Notification from './pages/Notification';
import OtherUserPage from './pages/OtherUserPage';
import RatingAdd from './pages/RatingAdd';
import RatingList from './pages/RatingList';
import SignUp from './pages/SignUp';
import TabnidaList from './pages/TabnidaList';
import TabnidaDetail from './pages/TabnidaDetail';
import TabnidaAdd from './pages/TabnidaAdd';
import TaeoondaAdd from './pages/TaeoondaAdd';
import TabnidaEdit from './pages/TabnidaEdit';
import TaeoondaEdit from './pages/TaeoondaEdit';
import TaeoondaDetail from './pages/TaeoondaDetail';
import TaeoondaList from './pages/TaeoondaList';
import RegisterList from './pages/RegisterList';
import RegisterCheckList from './pages/RegisterCheckList';

function App() {
  return (
    <>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <Screen>
          <Routes>
            <Route path="/" element={<Homepage />}></Route>
            <Route path="/chattingdetail" element={<ChattingDetail />}></Route>
            <Route path="/chattinglist" element={<ChattingList />}></Route>
            <Route path="/journeylist" element={<JourneyList />}></Route>
            <Route path="/loading" element={<Loading />}></Route>
            <Route path="/login" element={<LogIn />}></Route>
            <Route path="/map" element={<Map />}></Route>
            <Route path="/mypage" element={<MyPage />}></Route>
            <Route path="/notification" element={<Notification />}></Route>
            <Route path="/otheruserpage" element={<OtherUserPage />}></Route>
            <Route path="/ratingadd" element={<RatingAdd />}></Route>
            <Route path="/ratinglist" element={<RatingList />}></Route>
            <Route path="/signup" element={<SignUp />}></Route>
            <Route path="/tabnidaadd" element={<TabnidaAdd />}></Route>
            <Route path="/tabnidaedit" element={<TabnidaEdit />}></Route>
            <Route path="/tabnidadetail" element={<TabnidaDetail />}></Route>
            <Route path="/tabnidalist" element={<TabnidaList />}></Route>
            <Route path="/taeoondaadd" element={<TaeoondaAdd />}></Route>
            <Route path="/taeoondaedit" element={<TaeoondaEdit />}></Route>
            <Route path="/taeoondadetail" element={<TaeoondaDetail />}></Route>
            <Route path="/taeoondalist" element={<TaeoondaList />}></Route>
            <Route path="/registerlist" element={<RegisterList />}></Route>
            <Route path="/registerchecklist" element={<RegisterCheckList />}></Route>
          </Routes>
        </Screen>
      </ThemeProvider>
    </>
  );
}

export default App;
