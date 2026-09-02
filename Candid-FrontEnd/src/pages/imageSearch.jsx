import CHeader from "../compnents/cheader copy"
import SearchBar from "../compnents/searchBar"
import { useEffect } from "react"
import { jwtService } from "../logic/jwt"

function ImageSearch(){

    const tokenService = new jwtService()

    useEffect(() => {

        tokenService.checkTokenClient()

    }, [])

    return(

        <div className="
        
            relative
            flex
            flex-col
            items-center
        
        ">
            <CHeader active="Search"/>
            <div className="
            
                
                absolute
                top-0
                mt-2.5
            
            ">
                <SearchBar />
            </div>
        </div>

    )
    
}

export default ImageSearch