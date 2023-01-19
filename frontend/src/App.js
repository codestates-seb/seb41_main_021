import './App.css';
import GlobalStyle from './styles/GlobalStyle';
import { Route, Routes } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { Provider } from 'react-redux';
import store from './redux/store.jsx';
import theme from './styles/theme';
import Screen from './components/Screen';
import DriverAuth from './pages/DriverAuth';
import Homepage from './pages/Homepage';
import JourneyList from './pages/JourneyList';
import Loading from './pages/Loading';
import LogIn from './pages/LogIn';
import MyPage from './pages/MyPage';
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
import DestinationList from './components/Tayo/DestinationList';
import DestinationDetail from './components/Tayo/DestinationDetail';
import ExchagePage from './pages/ExchangePage';
import PointHistory from './pages/PointHistory';

import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
    <>
      <Provider store={store}>
        <ThemeProvider theme={theme}>
          <GlobalStyle />
          <Screen>
            <Routes>
              <Route path="/" element={<Homepage />}></Route>
              <Route path="/driver-auth" element={<DriverAuth />}></Route>
              <Route path="/journey-list" element={<JourneyList />}></Route>
              <Route path="/loading" element={<Loading />}></Route>
              <Route path="/login" element={<LogIn />}></Route>
              <Route path="/my-page" element={<MyPage />}></Route>
              <Route path="/other-user-page" element={<OtherUserPage />}></Route>
              <Route path="/rating-add" element={<RatingAdd />}></Route>
              <Route path="/rating-list" element={<RatingList />}></Route>
              <Route path="/signup" element={<SignUp />}></Route>
              <Route path="/tabnida-add" element={<TabnidaAdd />}></Route>
              <Route path="/tabnida-edit" element={<TabnidaEdit />}></Route>
              <Route path="/tabnida-detail" element={<TabnidaDetail />}></Route>
              <Route path="/tabnida-list" element={<TabnidaList />}></Route>
              <Route path="/taeoonda-add" element={<TaeoondaAdd />}></Route>
              <Route path="/taeoonda-edit" element={<TaeoondaEdit />}></Route>
              <Route path="/taeoonda-detail" element={<TaeoondaDetail />}></Route>
              <Route path="/taeoonda-list" element={<TaeoondaList />}></Route>
              <Route path="/register-list" element={<RegisterList />}></Route>
              <Route path="/register-checklist" element={<RegisterCheckList />}></Route>
              <Route path="/destination-list" element={<DestinationList />}></Route>
              <Route path="/destination-detail" element={<DestinationDetail />}></Route>
              <Route path="/exchange-page" element={<ExchagePage />}></Route>
              <Route path="/point-history" element={<PointHistory />}></Route>
            </Routes>
            <ToastContainer position="top-center" />
          </Screen>
        </ThemeProvider>
      </Provider>
    </>
  );
}

export default App;
