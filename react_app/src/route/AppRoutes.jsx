import { Route, Routes } from "react-router-dom";
import List from "../components/Board/List";
import Index from "../components/Index";
import Add from "../components/Board/Add";
import StudyParam from "../study/StudyParam";
import Login from "../components/member/Login";

export default function AppRoutes() {

    return(
        <>
            <Routes>
                <Route path="/" element={<Index/>}></Route>
                <Route path="/notice/">
                    <Route path="list" element={<List></List>}></Route>
                    <Route path="add" element={<Add></Add>}></Route>
                </Route>
                <Route path="/member/">
                    <Route path="Login" element={<Login></Login>}></Route>
                    
                </Route>

                <Route path="/study/param" element={<StudyParam/>}></Route>
                <Route path="/study/param/:num/:name" element={<StudyParam/>}></Route>
            </Routes>
        </>
    )
}