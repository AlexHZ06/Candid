import PHeader from "../compnents/pheader"
import { Link } from "react-router-dom"
import TagsCatSelection from "../compnents/tagsCatSelection"
import { useEffect, useState } from "react"
import { jwtService } from "../logic/jwt"
import { useParams } from "react-router-dom"
import Masonry from "react-masonry-css"

function Album(){

    const tokenService = new jwtService()
    const [albums, setAlbums] = useState([])
    const { albumId } = useParams()
    const {albumName} = useParams()
    const [images, setImages] = useState([])

    useEffect(() => {

        tokenService.checkTokenPhotographer()

        fetch("/api/album/photographer/getallalbums", {

            method:"GET",
            headers:{

                auth: localStorage.getItem("jwt")

            }
        }).then(response => response.json()).then(data => {

            setAlbums(data.data)
            console.log(data.data)

        })

        fetch("/api/album/photographer/getphotos",{

            method:"POST",
            headers:{
                
                "Content-Type": "application/json",
                auth:localStorage.getItem("jwt")

            },
            body:JSON.stringify({

                albumId:albumId

            })

        }).then(response => response.json()).then(data =>{

            setImages(data.data)

        })

    }, [albumId])

    return(

        <div className="
        
            w-screen 
            min-h-screen 
        
        
        ">
            <PHeader active="Gallery"/>
            <div className="
            
                flex
                flex-row
                h-[calc(100vh-3.75rem)]
                w-full

            
            ">

                <div className="
                
                    flex
                    flex-col
                    border-r-2
                    border-neutral-200
                    h-full
                    relative
                    w-[15vh]
                    items-center
                    pt-3
                    
                    
                
                ">
                    
                    <div className="
                    
                        flex
                        flex-col
                        gap-3
                        text-neutral-500

                        h-6/10
                        w-[15vh]
                        shrink-0
                        overflow-y-auto
                    
                    ">
                        
                        <p className="
                        
                            font-bold
                            text-black
                            pl-2
                        
                        ">Albums</p>
                        {albums.map(album =>(

                            <Link to={`/album/${album.albumid}/${album.albumname}`} key={album.albumid} className={`
                                
                                ml-5
                                hover:text-black
                                active:text-neutral-500
                                ${album.albumid === Number(albumId) ? "text-black font-semibold": ""}
                            
                            `}>{album.albumname}</Link>

                        ))}                                      
                    </div>
                    <div className="
                    
                        
                        flex
                        flex-col
                        justify-end
                        gap-5
                        h-3/10
                        mt-10
                    
                    ">
                        <Link to={`/addimage/${albumId}/${albumName}`} className="
                        
                            bg-neutral-700
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-gray-900
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        
                        
                        ">Add Photo</Link>
                        <Link to={`/albumedit/${albumId}/${albumName}`} className="
                        
                            bg-neutral-700
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-gray-900
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        
                        
                        ">Edit Album
                        </Link>
                        <Link to={"/gallery"} className="
                        
                            bg-red-600
                            text-white
                            rounded-xl
                            w-[10vh]
                            h-8.75
                            flex
                            items-center
                            justify-center
                             hover:bg-red-700
      
                            transition
                            duration-100

                            shadow-[0_0px_10px_rgba(0,0,0,0.25)]

                            active:bg-white
                            active:text-black

                        
                        
                        ">back</Link>

                    </div>

                </div>
                <div className="
                
                    flex
                    w-full
                    justify-center
                    items-center
                    flex-col

                ">
                    <p className="
                    
                        mb-2
                        text-2xl
                    
                    ">{albumName}</p>
                    <div className="

                       
                        w-[140vh]
                        h-9/10
                        flex
                        overflow-y-auto
                    
                    
                    ">
                        <Masonry
                            breakpointCols={{
                                default: 3,
                                1100: 3,
                                700: 2,
                                500: 1
                            }}
                            className="flex gap-4"
                            columnClassName="flex flex-col gap-4"
                        >
                            {images?.length > 0 ? (

                                images.map(image => (

                                    <img src={"/api" + image.thumbnailurl} className="

                                        m-1
                                        rounded-xl
                                        shadow-[0_0px_10px_rgba(0,0,0,0.10)]
                                    
                                    "/>

                                ))

                            ): ""}
                        </Masonry>
                    </div>
                </div>

            </div>
        </div>

    )

}

export default Album