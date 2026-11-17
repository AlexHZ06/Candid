import CHeader from "../compnents/cheader"
import SearchBar from "../compnents/searchBar"
import { useEffect, useRef } from "react"
import { Link, useLocation } from "react-router-dom"
import { jwtService } from "../logic/jwt"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import Masonry from "react-masonry-css"
import TagsCatSelection from "../compnents/tagsCatSelection"

function ImageSearch() {

    const location = useLocation()
    const profile = location.state.profile
    const jwt = new jwtService()
    const [feed, setFeed] = useState([])
    const navigate = useNavigate();
    const searchBar = useRef();

    useEffect(() => {

        getFeed()



    }, [])

    const [hidden, setHidden] = useState(true)

    function getFeed() {

        fetch("/api/feed/client/getfeed", {

            method: "POST",
            headers: {

                "Content-Type": "application/json",
                auth: localStorage.getItem("jwt")

            },
            body: JSON.stringify({

                search: searchBar.current.value,
                category: profile.projectcatagory,
                userId: profile.userid,
                minPrice: -1,
                maxPrice: -1,
                radius: -1,
                profileId: profile.profileid

            })

        }).then(response => response.json()).then(async data => {

            if (!data.success) {

                if (data.internalCode == 401) {

                    let result = await jwt.requestJwt();
                    if (result) {

                        fetch("/api/feed/client/getfeed", {

                            method: "POST",
                            headers: {

                                "Content-Type": "application/json",
                                auth: localStorage.getItem("jwt")

                            },
                            body: JSON.stringify({

                                search: searchBar.current.value,
                                category: profile.projectcatagory,
                                userId: profile.userid,
                                minPrice: -1,
                                maxPrice: -1,
                                radius: -1,
                                profileId: profile.profileid

                            })

                        }).then(res => res.json()).then(d => {

                            if (!d.success) {

                                console.log("error")

                            }
                            else {

                                console.log(d)
                                setFeed(d.data.flat())

                            }

                        })

                    }

                } else {

                    console.log("error")

                }

            } else {

                console.log(data)
                setFeed(data.data.flat())

            }

        })

    }

    function filterPress() {

        if (hidden) {

            setHidden(false)

        }
        else {

            setHidden(true)

        }

    }

    function imageClick(photo) {

        navigate("/imagecard", {

            state: {

                photo,
                profile

            }

        })

    }

    return (

        <div className="
        
            relative
            flex
            flex-col
            items-center
        
        ">
            <CHeader active="Search" />
            <div className="
            
                
                absolute
                top-0
                mt-2.5
            
            ">
                <div className="
                
                    flex
                    gap-2
                    justify-center
                    items-center
                    flex-col
                    

                ">
                    <div className="

                    flex 
                    gap-2
                    mb-3
                    items-center

                ">
                        <input ref={searchBar} placeholder="What are we looking for ?" type="text" className="
                    
                        border-2
                        border-neutral-300
                        rounded-4xl
                        pl-2



                        w-[50vh]
                        h-[6vh]

                        2xl:w-[30vh]
                        2xl:h-[4vh]

                        hover:border-neutral-400
                        active:border-neutral-500
                        hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                        focus:ring-0
                    
                    "/>
                        <button onClick={getFeed} className="
                    
                        2xl:h-[4vh]
                        2xl:w-[4vh]
                        h-[6vh]
                        w-[6vh]
                        border-2
                        border-neutral-300
                        rounded-4xl
                        p-2
                        hover:border-neutral-400
                        hover:shadow-[0_0_10px_rgba(0,0,0,0.25)]
                        active:border-black

                    ">
                            <img src="src/resources/Search--Streamline-Ultimate.png" />
                        </button>
                        <Link className="
                    
                        text-neutral-600
                        hover:text-black
                        active:text-neutral-600
                    
                    ">See Profile</Link>
                    </div>
                </div>

            </div>
            <div className="
            
                w-full
                h-full
                overflow-y-auto
                [&::-webkit-scrollbar]:hidden
                [-ms-overflow-style:none]
                scrollbar-width:none
                p-10
            
            ">
                <Masonry
                    columnClassName="
                        pl-4 
                        bg-clip-padding
                    "
                    breakpointCols={{
                        default: 3,
                        1100: 3,
                        700: 2,
                        500: 1
                    }}
                    className="
                    
                        flex 
                        w-full
                        -ml-4
                        gap-5

                    "
                >
                    {feed.map((item) => (
                        <button onClick={() => { imageClick(item.photos) }} key={item.photos.photoid} className="
                        
                            mb-4 
                            overflow-hidden 
                            rounded-xl
                            hover:shadow-[0_0px_10px_rgba(0,0,0,0.25)]
                            active:shadow-[0_0px_10px_rgba(0,0,0,0.25)_inset]

                        "
                        >
                            <img
                                src={"/api" + item.photos.thumbnailurl}
                                alt={item.photos.photoname}
                                className="block w-full h-auto rounded-xl"
                            />
                        </button>
                    ))}
                </Masonry>
            </div>
        </div>

    )

}

export default ImageSearch